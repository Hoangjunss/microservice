import { Component, OnInit } from '@angular/core';
import { Project } from '../../model/project';
import { ProjectServiceService } from '../../service/project-service.service';
import { CommonModule, DatePipe } from '@angular/common';

@Component({
  selector: 'app-project-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './project-list.component.html',
  styleUrl: './project-list.component.css'
})
export class ProjectListComponent implements OnInit{

  projects:Project[]=[];

  project1: Project[] = [
    { id: 0, title: 'facebook', description: 'làm trang mạng xã hội về facebook', createAt: '8-8-2024' },
    { id: 1, title: 'zalo', description: 'làm trang mạng xã hội về zalo', createAt: '8-8-2024' },
    { id: 2, title: 'instagram', description: 'làm trang mạng xã hội về instagram', createAt: '8-8-2024' },
    { id: 3, title: 'X', description: 'làm trang mạng xã hội về X', createAt: '8-8-2024' }
  ];

  constructor(private projectService:ProjectServiceService, private datePipe: DatePipe){}

  ngOnInit(): void {
    console.log("Project start");
    this.getProjectByIdProfile(1);
  }


  getFormattedDate(createAt?:string) {
    return this.datePipe.transform(createAt, 'MMM d, y');
  }

  getProjectByUser(id:number){
    this.projectService.getProjectByUser(id).subscribe(data=>{
      this.projects=data;
    })
  }

  getProjectByIdProfile(id:number){
    this.projectService.getProjectByIdProfile(id).subscribe(data=>{
      this.projects=data;
    })
  }


}
