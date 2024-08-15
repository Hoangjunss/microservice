import { CommonModule, DatePipe } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { ProjectServiceService } from '../../service/project-service.service';
import { Project } from '../../model/project';
import { RouterOutlet } from '@angular/router';
import { PostProjectComponent } from "../post-project/post-project.component";
import { Profile } from '../../model/profile';
import { ProfileServiceService } from '../../service/profile-service.service';

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
  userCurrent: any;
  profile?: Profile;
  @Input() idProfile?:number | null;

  constructor(private projectService:ProjectServiceService, private datePipe: DatePipe,private profileService:ProfileServiceService){}

  ngOnInit(): void {
    const userCurrentString = localStorage.getItem('userCurrent');
    if (userCurrentString) {
      this.userCurrent = JSON.parse(userCurrentString);
    }
    console.log("Project start");
    const idProfile = localStorage.getItem('idProfile');
    console.log("idProfile",idProfile);
    if(idProfile){
      this.getProjectByIdProfile(Number(idProfile));
    }else{
      this.getProfileByIdUser(this.userCurrent.id);
    }
  }

  getFormattedDate(createAt?:string) {
    return this.datePipe.transform(createAt, 'MMM d, y');
  }

  getProjectByUser(id:number){
    this.projectService.getProjectByUser(id).subscribe(data=>{
      this.projects=data
      this.isCurrentUserProfile();
    })
  }

  getProfileByIdUser(id:number){
    this.profileService.getProfileByUserId(id).subscribe(data=>{
      this.profile=data;
      if(this.profile.id)
      {
        this.getProjectByIdProfile(this.profile.id);
      }
    })
  }

  getProjectByIdProfile(id:number){
    this.projectService.getProjectByIdProfile(id).subscribe(data=>{
      this.projects=data;
    })
  }
  getProfileById(idProfile: number) {
    this.profileService.getProfileById(idProfile).subscribe(data => {
      this.profile = data;
      if(this.profile.idUser)
      {
        this.getProjectByUser(this.profile.idUser);
      }
    });
  }

  deleteProject(project:Project){
    this.projectService.deleteProjectByUser(project.id).subscribe(data=>{
      if(data){
        console.log(data);
        alert('Delete project successfully');
        this.getProjectByIdProfile(this.idProfile!);
      }
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

  isCurrentUserProfile(): boolean {
    return this.profile?.idUser === this.userCurrent?.id;
  }

}
