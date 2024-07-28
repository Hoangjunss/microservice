import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Profile } from './profile';
import { map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProfileServiceService {

  constructor(private httpClient:HttpClient) { }
  private baseURL = "http://localhost:8085/profile/getAll";

  
  getProfilesList(): Observable<Profile[]> {
    return this.httpClient.get<any[]>(this.baseURL).pipe(
      map(profileDTOs => profileDTOs.map(this.mapToProfile))
    );
  }

  private mapToProfile(profileDTO: any): Profile {
    return {
      id: profileDTO.id,
      objective: profileDTO.objective,
      education: profileDTO.education,
      workExperience: profileDTO.workExperience
    };
  }
}
