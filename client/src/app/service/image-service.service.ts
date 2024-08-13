import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Apiresponse } from '../apiresponse';
import { map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ImageServiceService {

  private baseURL="http://localhost:8080/image"
  constructor(private http:HttpClient) { }
  

  uploadImage(image: File): Observable<any> {
    const headers = this.createAuthorizationHeader();
    const formData = new FormData();
    formData.append('imageFile', image);
    console.log('Form Data:', formData.get('imageFile')); 
    return this.http.post<Apiresponse<any>>(`${this.baseURL}/save`,formData, { headers }).pipe(
      map(response => {
        if(response.success){
          return response.data;
        }else{
          throw new Error(response.message);
        }
      })
    );
  }

  private createAuthorizationHeader(): HttpHeaders {
    const token = localStorage.getItem('authToken');
    if(token){
      console.log('Token found in local store:', token);
      return new HttpHeaders().set('Authorization', `Bearer ${token}`);
    }
    else
    {
      console.log('Token not found in local store');
    }
    return new HttpHeaders();
  }

}
