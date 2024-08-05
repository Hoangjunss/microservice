import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Project } from '../model/project';

@Injectable({
  providedIn: 'root'
})
export class ProjectServiceService {

  constructor(private http:HttpClient) { }
  getProjectByUser(id:number):Observable<Project[]>{
    return this.http.get<any>('http://localhost:8086/project/getByUser').pipe(map(project=>project.map(this.conventToProject)));
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
    return this.http.post<any>('http://localhost:8086/save',project).pipe(map(this.conventToProject));
  }
  updateProjectByUser(project:Project):Observable<Project>{
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<any>('http://localhost:8086/update',project, {headers}).pipe(map(this.conventToProject));
  }

  getProjectByIdProfile(id?:number):Observable<Project[]>{
    return this.http.get<any[]>('http://localhost:8086/getProject?id='+id)
    .pipe(map(projectDTO=>projectDTO.map(this.mapToProject)));
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
