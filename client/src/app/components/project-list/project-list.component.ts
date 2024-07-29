import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { ProjectServiceService } from '../../service/project-service.service';
import { Project } from '../../model/project';

@Component({
  selector: 'app-project-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './project-list.component.html',
  styleUrl: './project-list.component.css'
})
export class ProjectListComponent implements OnInit {
  projects:Project[]=[]
  project:Project=new Project;
  @Input() idUser?:number;
  constructor(private projectService:ProjectServiceService){}
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }
  getProjectByUser(id:number){
    this.projectService.getProjectByUser(id).subscribe(data=>{
      this.projects=data
    })
  }
 

}
