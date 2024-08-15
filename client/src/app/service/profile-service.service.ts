import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Profile } from '../model/profile';
import { catchError, map, Observable, throwError } from 'rxjs';
import { Apiresponse } from '../apiresponse';
import { Router } from '@angular/router'; 

@Injectable({
  providedIn: 'root'
})
export class ProfileServiceService {

  constructor(private httpClient:HttpClient,private router:Router) { }
  private baseURL = 'http://localhost:8080/profile/';

  
  getProfilesList(): Observable<Profile[]> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<Profile[]>>(`${this.baseURL}user/getAll`,{headers}).pipe(
      map(response => {
        if (response.success) {
          return response.data.map(this.mapToProfile);
        } else {
          throw new Error(response.message);
        }
      }),
      // catchError(
      //   error => {
      //     if (error instanceof HttpErrorResponse && error.status === 401) {
      //       console.error('Unauthorized:', error);
      //       this.router.navigate(['/login']);
      //       localStorage.removeItem('authToken');
      //       localStorage.removeItem('userCurrent');
      //     }
      //     console.error('Error fetching profiles:', error);
      //     return throwError(() => new Error('Something went wrong!'));
      //   }
      // )
    );
  }

  getListProfileByIdPendingJob(id:number[]): Observable<Profile[]> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<Profile[]>>(`${this.baseURL}manager/getProfileByIdPendingJob?ids=${id}`, {headers}).pipe(
      map(response => {
        if (response.success) {
          return response.data.map(this.mapToProfile);
        } else {
          throw new Error(response.message);
        }
      }),
      // catchError(
      //   error => {
      //     if (error instanceof HttpErrorResponse && error.status === 401) {
      //       console.error('Unauthorized:', error);
      //       this.router.navigate(['/login']);
      //       localStorage.removeItem('authToken');
      //       localStorage.removeItem('userCurrent');
      //     }
      //     console.error('Error fetching profiles:', error);
      //     return throwError(() => new Error('Something went wrong!'));
      //   }
      // )
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
      // catchError(
      //   error => {
      //     if (error instanceof HttpErrorResponse && error.status === 401) {
      //       console.error('Unauthorized:', error);
      //       this.router.navigate(['/login']);
      //       localStorage.removeItem('authToken');
      //       localStorage.removeItem('userCurrent');
      //     }
      //     console.error('Error fetching profiles:', error);
      //     return throwError(() => new Error('Something went wrong!'));
      //   }
      // )
    );
  }

  updateProfile(profile: FormData): Observable<Profile> {
   
    const headers = this.createAuthorizationHeader();
   
    return this.httpClient.post<Apiresponse<Profile>>(`${this.baseURL}user/update`, profile, { headers }).pipe(
      map(response => {
        if (response.success) {
          return this.mapToProfile(response.data);
        } else {
          throw new Error(response.message);
        }
      }),
      // catchError(
      //   error => {
      //     if (error instanceof HttpErrorResponse && error.status === 401) {
      //       console.error('Unauthorized:', error);
      //       this.router.navigate(['/login']);
      //       localStorage.removeItem('authToken');
      //       localStorage.removeItem('userCurrent');
      //     }
      //     console.error('Error fetching profiles:', error);
      //     return throwError(() => new Error('Something went wrong!'));
      //   }
      // )
    );
  }

  getProfileByType(type: string): Observable<Profile[]> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<Profile[]>>(`${this.baseURL}user/findProfileByType?typeProfile=${type}`, {headers}).pipe(
      map(response => {
        if (response.success) {
          return response.data.map(this.mapToProfile);
        } else {
          throw new Error(response.message);
        }
      }),
      // catchError(
      //   error => {
      //     if (error instanceof HttpErrorResponse && error.status === 401) {
      //       console.error('Unauthorized:', error);
      //       this.router.navigate(['/login']);
      //       localStorage.removeItem('authToken');
      //       localStorage.removeItem('userCurrent');
      //     }
      //     console.error('Error fetching profiles:', error);
      //     return throwError(() => new Error('Something went wrong!'));
      //   }
      // )
    );
  }

  getProfileById(id:number):Observable<Profile>{
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<any>(`${this.baseURL}user/findById?id=${id}`, {headers})
   .pipe(map(response=>{
     if(response.success){
       return this.mapToProfile(response.data);
     }
     else{
       throw new Error(response.message);
     }
   }),
  //  catchError(
  //   error => {
  //     if (error instanceof HttpErrorResponse && error.status === 401) {
  //       console.error('Unauthorized:', error);
  //       this.router.navigate(['/login']);
  //       localStorage.removeItem('authToken');
  //       localStorage.removeItem('userCurrent');
  //     }
  //     console.error('Error fetching profiles:', error);
  //     return throwError(() => new Error('Something went wrong!'));
  //   }
  // )
  );
  }

  getProfileByUserId(userId:number): Observable<Profile>{
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<Profile>>(`${this.baseURL}user/findByUserId?userId=${userId}`, { headers }).pipe(
      map(response => {
        if (response.success) {
          return this.mapToProfile(response.data);
        } else {
          throw new Error(response.message);
        }
      }),
      // catchError(
      //   error => {
      //     if (error instanceof HttpErrorResponse && error.status === 401) {
      //       console.error('Unauthorized:', error);
      //       this.router.navigate(['/login']);
      //       localStorage.removeItem('authToken');
      //       localStorage.removeItem('userCurrent');
      //     }
      //     console.error('Error fetching profiles:', error);
      //     return throwError(() => new Error('Something went wrong!'));
      //   }
      // )
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
      idUser:profileDTO.idUser,
      title: profileDTO.title,
      contact: profileDTO.contact,
      skills: profileDTO.skills,
      typeProfile: profileDTO.typeProfile,
      url: profileDTO.url
    };
  }
}
