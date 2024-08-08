import { Apiresponse } from './../apiresponse';
import { Injectable } from '@angular/core';
import { Contact } from '../model/contact';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ContactServiceService {
  
  constructor(private http:HttpClient) { }
  getContactByUser(id:number):Observable<Contact>{
    const headers = this.createAuthorizationHeader();
    return this.http.get<Apiresponse<Contact>>('http://localhost:8080/contact/getByUser?idUser'+id, {headers}).pipe(
      map(response=>{
        if(response.success){
          return response.data;
        }
        else{
          throw new Error(response.message);
        }
      })
    );
  }
  createContactByUser(contact:Contact):Observable<Contact>{
    const headers = this.createAuthorizationHeader();
    return this.http.post<Apiresponse<Contact>>('http://localhost:8080/contact/save',contact, {headers}).pipe(
      map(response=>{
        if(response.success){
          return response.data;
        }
        else{
          throw new Error(response.message);
        }
      })
    );
  }
  updateContactByUser(contact:Contact):Observable<Contact>{
    const headers = this.createAuthorizationHeader();
    return this.http.put<Apiresponse<Contact>>('http://localhost:8080/contact/update',contact, {headers}).pipe(
      map(response=>{
        if(response.success){
          return response.data;
        }
        else{
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
