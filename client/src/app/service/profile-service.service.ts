import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Profile } from '../model/profile';
import { catchError, map, Observable, throwError } from 'rxjs';
import { title } from 'process';

@Injectable({
  providedIn: 'root'
})
export class ProfileServiceService {

  constructor(private httpClient:HttpClient) { }
  private baseURL = 'http://localhost:8085/profile/save';

  
  getProfilesList(): Observable<Profile[]> {
    return this.httpClient.get<any[]>('http://localhost:8085/getAll').pipe(
      map(profileDTOs => profileDTOs.map(this.mapToProfile))
    );
  }
  createProfile(profile: Profile):Observable<Profile> {
    console.log('Creating profile:', profile);

    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.httpClient.post<any>(this.baseURL, profile, { headers }).pipe(
      map(this.mapToProfile),
      catchError(error => {
        console.error('Error creating profile:', error);
        return throwError(() => new Error(error));
      })
    );
  }
  getProfileByType(type:string):Observable<Profile[]>{
    return this.httpClient.get<any[]>('http://localhost:8085/profile/findProfileByType?typeProfile='+type)
    .pipe(map(profileDTO=>profileDTO.map(this.mapToProfile)));
  }
  getProfileByUser(id:number):Observable<Profile>{
    return this.httpClient.get<any[]>('http://localhost:8085/profile/findProfileByType?idUser='+id)
    .pipe(map(this.mapToProfile));
  }
  private mapToProfile(profileDTO: any): Profile {
    return {
      id: profileDTO.id,
      objective: profileDTO.objective,
      education: profileDTO.education,
      workExperience: profileDTO.workExperience,
      title: profileDTO.title
    };
  }
}
