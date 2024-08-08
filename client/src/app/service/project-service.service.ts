import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Project } from '../model/project';
import { Apiresponse } from '../apiresponse';
import { response } from 'express';

@Injectable({
  providedIn: 'root'
})
export class ProjectServiceService {

  constructor(private http:HttpClient) { }
  getProjectByUser(id:number):Observable<Project[]>{
    const headers = this.createAuthorizationHeader();
    return this.http.get<Apiresponse<Project[]>>('http://localhost:8088/project/getByUser', {headers})
    .pipe(map(response=>{
      if(response.success){
        return response.data;
      }
      else{
        throw new Error(response.message);
      }
    }));
  }
  conventToProject(project :any):Project{
      return {
        id:project.id,
        title:project.title,
        description:project.description,
        createAt:project.createAt,
        url:project.url,
        display:project.display,
        idProfile:project.idProfile
      }
  }
  createProjectByUser(project:Project):Observable<Project>{
    let headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const authHeaders = this.createAuthorizationHeader();
    if (authHeaders.has('Authorization')) {
        headers = headers.set('Authorization', authHeaders.get('Authorization')!);
    }
    return this.http.post<Apiresponse<Project>>('http://localhost:8080/project/save', project, { headers }).pipe(
      map(response => {
        if (response.success) {
          return response.data;
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  updateProjectByUser(project:Project):Observable<Project>{
    const headers = this.createAuthorizationHeader();
    return this.http.post<Apiresponse<Project>>('http://localhost:8080/project/update',project, {headers}).pipe(
      map(response=>{
        if(response.success){
          return this.mapToProject(response.data);
        }
        else{
          throw new Error(response.message);
        }
      }));
  }

  deleteProjectByUser(id?:number):Observable<string>{
    const headers = this.createAuthorizationHeader();
    return this.http.delete<Apiresponse<string>>(`http://localhost:8080/project/delete/${id}`, {headers}).pipe(
      map(response => {
        if (!response.success) {
          throw new Error(response.message);
        }else{
          return response.data;
        }
      })
    );
  }

  getProjectByIdProfile(id?:number):Observable<Project[]>{
    const headers = this.createAuthorizationHeader();
    return this.http.get<Apiresponse<Project[]>>('http://localhost:8080/project/getProject?id='+id, {headers}).pipe(
      map(response=>{
        if(response.success){
          return response.data.map(this.mapToProject);
        }
        else{
          throw new Error(response.message);
        }
      }));
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

  private mapToProject(projectDTO: any): Project{
    return {
      id: projectDTO.id,
      title: projectDTO.title,
      description: projectDTO.description,
      createAt: projectDTO.createAt,
      url: projectDTO.url,
      display: projectDTO.display,
      idProfile: projectDTO.idProfile
    }
  }
}
