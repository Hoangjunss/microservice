import { Component, Input, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ProjectServiceService } from '../../service/project-service.service';
import { Project } from '../../model/project';

@Component({
  selector: 'app-post-project',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './post-project.component.html',
  styleUrl: './post-project.component.css'
})
export class PostProjectComponent implements OnInit {
  projects:Project[]=[]
  project:Project=new Project;
  @Input() idUser?:number;
  constructor(private projectService:ProjectServiceService){}
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }
  createProjectByUser(project:Project){
    this.projectService.createProjectByUser(project).subscribe(data=>{
      this.project=data
    })
  }
  updateProjectByUser(project:Project){
    this.projectService.updateProjectByUser(project).subscribe(data=>{
      this.project=data
    })
  }
}
