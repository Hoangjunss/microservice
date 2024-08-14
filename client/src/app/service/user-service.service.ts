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

   updateUser(user: User): Observable<User> {
    const token = localStorage.getItem('authToken');
    console.log('Token:', token); // Log the token
    return this.http.post<Apiresponse<User>>(`${this.baseURL}/updateactive?token=${token}`, user).pipe(
      map(response => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  update(user: User): Observable<User> {
    const token = localStorage.getItem('authToken');
    console.log('Token:', token); // Log the token
    return this.http.post<Apiresponse<User>>(`${this.baseURL}/update?token=${token}`, user).pipe(
      map(response => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  deleteUser(id: number): Observable<User> {
    const token = localStorage.getItem('authToken');
    return this.http.delete<Apiresponse<User>>(`${this.baseURL}/delete?token=${token}&id=${id}`).pipe(
      map(response => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getUserById(id:number): Observable<User>{
    const token = localStorage.getItem('authToken');
    return this.http.get<Apiresponse<User>>(`${this.baseURL}/findbyid?id=${id}&token=${token}`).pipe(
      map(response => {
        if (response.success) {
          return response.data;
        } else {
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
          console.log(response.data+" user service");
          return response.data;
        }else{
          throw new Error(response.message);
        }
      })
    );
   }

   getAllUser(): Observable<User[]>{
    const token = localStorage.getItem('authToken');
    return this.http.get<Apiresponse<User[]>>(this.baseURL+"/getAll?token="+token).pipe(
      map(response => {
        if(response.success){
          return response.data;
        }else{
          throw new Error(response.message);
        }
      })
    );
   }

   getListUserById(ids: number[]): Observable<User[]> {
    const token = localStorage.getItem('authToken');
    return this.http.get<Apiresponse<User[]>>(`${this.baseURL}/getlistuserbyid?token=${token}&ids=${ids}`).pipe(
        map(response => {
            if(response.success){
                return response.data;
            } else {
                throw new Error(response.message);
            }
        })
    );
  }


}
