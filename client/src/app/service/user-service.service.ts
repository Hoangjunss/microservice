import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../model/user';
import { map, Observable } from 'rxjs';
import { Apiresponse } from '../apiresponse';

@Injectable({
  providedIn: 'root'
})
export class UserServiceService {

  constructor(private http:HttpClient) { }
  private baseURL="http://localhost:8080/auth"
   signUpUser(user:User):Observable<User>{
    console.log(user+" user-service")
    return this.http.post<Apiresponse<User>>(this.baseURL+"/signup",user).pipe(
      map(response => {
        if(response.success){
          return response.data;
        }else{
          throw new Error(response.message);
        }
      })
    );
   }
   signInUser(user:User):Observable<User>{
    return this.http.post<any>(this.baseURL+"/signin",user).pipe(
      map(response => {
        if(response.success){
          return response.data;
        }else{
          throw new Error(response.message);
        }
      })
    );
   }

   getCurrentUser(): Observable<User>{
    const token = localStorage.getItem('authToken');
    return this.http.get<Apiresponse<User>>(this.baseURL+"/getCurrentUser?token="+token).pipe(
      map(response => {
        if(response.success){
          return response.data;
        }else{
          throw new Error(response.message);
        }
      })
    );
   }
}
