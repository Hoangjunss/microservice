import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Profile } from '../model/profile';
import { catchError, map, Observable, throwError } from 'rxjs';
import { Apiresponse } from '../apiresponse';
import { response } from 'express';

@Injectable({
  providedIn: 'root'
})
export class ProfileServiceService {

  constructor(private httpClient:HttpClient) { }
  private baseURL = 'http://localhost:8080/profile/save';

  
  getProfilesList(): Observable<Profile[]> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<Profile[]>>('http://localhost:8080/profile/getAll', {headers}).pipe(
      map(response => {
        if (response.success) {
          return response.data.map(this.mapToProfile);
        } else {
          throw new Error(response.message);
        }
      }),
      catchError(error => {
        console.error('Error fetching profile by user:', error);
        return throwError(() => new Error(error));
      })
    );
  }
  createProfile(profile: Profile):Observable<Profile> {
    console.log('Creating profile:', profile);
    let headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const authHeaders = this.createAuthorizationHeader();
    if (authHeaders.has('Authorization')) {
        headers = headers.set('Authorization', authHeaders.get('Authorization')!);
    }
    return this.httpClient.post<any>(this.baseURL, profile, { headers }).pipe(
      map(this.mapToProfile),
      catchError(error => {
        console.error('Error creating profile:', error);
        return throwError(() => new Error(error));
      })
    );
  }

  getProfileByType(type: string): Observable<Profile[]> {
    return this.httpClient.get<Apiresponse<Profile[]>>('http://localhost:8080/profile/findProfileByType?typeProfile=' + type).pipe(
      map(response => {
        if (response.success) {
          return response.data.map(this.mapToProfile);
        } else {
          throw new Error(response.message);
        }
      }),
      catchError(error => {
        console.error('Error fetching profiles by type:', error);
        return throwError(() => new Error(error));
      })
    );
  }

  getProfileByUser(id: number): Observable<Profile> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<Profile>>('http://localhost:8080/profile/findProfileByType?idUser=' + id, { headers }).pipe(
      map(response => {
        if (response.success) {
          return this.mapToProfile(response.data);
        } else {
          throw new Error(response.message);
        }
      }),
      catchError(error => {
        console.error('Error fetching profile by user:', error);
        return throwError(() => new Error(error));
      })
    );
  }

  getProfileById(id:number):Observable<Profile>{
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<any>(`http://localhost:8080/profile/findById?id=`+id, {headers})
   .pipe(map(response=>{
     if(response.success){
       return this.mapToProfile(response.data);
     }
     else{
       throw new Error(response.message);
     }
   }),
   catchError(error => {
    console.error('Error fetching profile by user:', error);
    return throwError(() => new Error(error));
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
  private mapToProfile(profileDTO: any): Profile {
    return {
      id: profileDTO.id,
      objective: profileDTO.objective,
      education: profileDTO.education,
      workExperience: profileDTO.workExperience,
      title: profileDTO.title,
      contact: profileDTO.contact
    };
  }
}
