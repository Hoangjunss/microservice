import { title } from 'process';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Job } from '../model/job';
import { map, Observable } from 'rxjs';
import { Apiresponse } from '../apiresponse';

@Injectable({
  providedIn: 'root'
})
export class JobServiceService {
  private job: Job | undefined;

  constructor(private httpClient:HttpClient) { }
  private baseURL="http://localhost:8080/manager/job";

  createJob(job: Job): Observable<Job>{
    let headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const authHeaders = this.createAuthorizationHeader();
    return this.httpClient.post<Apiresponse<Job>>(this.baseURL+'/create', JSON.stringify(job), {headers}).pipe(
      map(response => {
        if (response.success) {
          return this.mapToJob(response.data);
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  updateJob(job: Job): Observable<Job>{
    let headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const authHeaders = this.createAuthorizationHeader();
    return this.httpClient.put<Apiresponse<Job>>(this.baseURL+'/update', JSON.stringify(job), {headers}).pipe(
      map(response => {
        if (response.success) {
          return this.mapToJob(response.data);
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  deleteJob(id: number): void{
    let headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const authHeaders = this.createAuthorizationHeader();
    this.httpClient.delete<Apiresponse<any>>(this.baseURL+'/delete?id='+id, {headers}).subscribe(response => {
      if (!response.success) {
        throw new Error(response.message);
      }else{
        alert('Deleted Job successfully');
      }
    });
  }

  getJobById(id:number): Observable<Job>{
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<Job>>(this.baseURL+'/findbyid?id='+id, {headers}).pipe(
      map(response => {
        if (response.success) {
          return this.mapToJob(response.data);
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getAllJobs(): Observable<Job[]> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<Job[]>>(this.baseURL+'/getall', {headers}).pipe(
      map(response => {
        if (response.success) {
          return response.data.map(this.mapToJob);
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getJobByCompany(idCompany:number): Observable<Job[]>{
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<Job[]>>(this.baseURL+'/getjobbycompany?id='+idCompany, {headers}).pipe(
      map(response => {
        if (response.success) {
          return response.data.map(this.mapToJob);
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getJobPending(idProfile:number): Observable<Job[]> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<Job[]>>(this.baseURL+'/getjobpending?id='+idProfile, {headers}).pipe(
      map(response => {
        if (response.success) {
          return response.data.map(this.mapToJob);
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getJobAccepted(idProfile:number): Observable<Job[]> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<Job[]>>(this.baseURL+'/getjobaccepted?id='+idProfile, {headers}).pipe(
      map(response => {
        if (response.success) {
          return response.data.map(this.mapToJob);
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getNewJob(idProfile: number): Observable<Job[]> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<Job[]>>(this.baseURL+'/getnewjob?id='+idProfile, {headers}).pipe(
      map(response => {
        if (response.success) {
          return response.data.map(this.mapToJob);
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  setJob(job: Job) {
    this.job = job;
  }

  getJob(): Job | undefined {
    return this.job;
  }


  private mapToJob(jobDTO: any): Job{
    return {
      id: jobDTO.id,
      title: jobDTO.title,
      description: jobDTO.description,
      typeJob: jobDTO.typeJob,
      size: jobDTO.size,
      idProfilePending: jobDTO.idProfilePending,
      idProfile: jobDTO.idProfile,
      idCompany: jobDTO.idCompany
    }
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
