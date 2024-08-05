import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../model/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserServiceService {

  constructor(private http:HttpClient) { }
  private baseURL="http://localhost:8080/auth"
   signUpUser(user:User):Observable<User>{
    return this.http.post<any>(this.baseURL+"/signup",user);
   }
   signInUser(user:User):Observable<User>{
    return this.http.get<any>(this.baseURL+"/signin");
   }
}
