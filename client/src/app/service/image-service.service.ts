import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Apiresponse } from '../apiresponse';
import { catchError, map, Observable, throwError } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ImageServiceService {

  private baseURL="http://localhost:8080/image"
  constructor(private http:HttpClient,private router:Router) { }
  

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
      }),
      catchError(
        error => {
          if (error instanceof HttpErrorResponse && error.status === 401) {
            console.error('Unauthorized:', error);
            this.router.navigate(['/login']);
            localStorage.removeItem('authToken');
            localStorage.removeItem('userCurrent');
          }
          console.error('Error fetching profiles:', error);
          return throwError(() => new Error('Something went wrong!'));
        }
      )
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
