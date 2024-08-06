import { CommonModule, DatePipe } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { ProjectServiceService } from '../../service/project-service.service';
import { Project } from '../../model/project';
import { RouterOutlet } from '@angular/router';
import { PostProjectComponent } from "../post-project/post-project.component";

@Component({
  selector: 'app-project-list',
  standalone: true,
  imports: [CommonModule, RouterOutlet, PostProjectComponent],
  templateUrl: './project-list.component.html',
  styleUrl: './project-list.component.css',
  providers: [DatePipe] 
})
export class ProjectListComponent implements OnInit {
  projects:Project[]=[]
  project:Project=new Project;
  selectedProject: Project | null = null;
  isFormVisible: boolean = false;
  @Input() idProfile?:number;
  constructor(private projectService:ProjectServiceService, private datePipe: DatePipe){}
  ngOnInit(): void {
    console.log("Project start");
    if(this.idProfile){
      this.getProjectByIdProfile(this.idProfile);
    }
  }

  getFormattedDate(createAt?:string) {
    return this.datePipe.transform(createAt, 'MMM d, y');
  }

  getProjectByUser(id:number){
    this.projectService.getProjectByUser(id).subscribe(data=>{
      this.projects=data
    })
  }

  getProjectByIdProfile(id:number){
    this.projectService.getProjectByIdProfile(id).subscribe(data=>{
      this.projects=data;
    })
  }

  showEditForm(project:Project | null): void {
    console.log(project?.title);
    this.selectedProject = project;
    this.isFormVisible = true;
  }

  hideEditForm(): void {
    this.selectedProject = null; 
    this.isFormVisible = false;
  }

}
