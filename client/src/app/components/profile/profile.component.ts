import { Component, Input, OnInit } from '@angular/core';
import { ContactComponent } from '../contact/contact.component';
import { CreateProfileComponent } from '../create-profile/create-profile.component';
import { RouterOutlet } from '@angular/router';
import { Profile } from '../../model/profile';
import { ProfileServiceService } from '../../service/profile-service.service';
import { ProjectServiceService } from '../../service/project-service.service';
import { Project } from '../../model/project';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [ContactComponent,CreateProfileComponent,RouterOutlet, CommonModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit{
  @Input() param?: number;
  constructor(private profileService: ProfileServiceService, private projectService: ProjectServiceService) { }
  id?:number;
  profile: Profile = new Profile;
  projects: Project[] = [];
  ngOnInit(): void {
    if (this.param) {
      this.getByUser(this.param);
    }
    else{
      this.id =1;
      this.getProfileById(this.id);
    }
  }

  getByUser(id: number) {
    this.profileService.getProfileByUser(id).subscribe(data => {
      this.profile = data;
    })
  }
  getProfileById(id:number){
    this.profileService.getProfileById(id).subscribe(data=>{
      this.profile=data;
      this.getProjectByIdProfile(this.id);
    })
  }

  getProjectByIdProfile(id?:number){
    this.projectService.getProjectByIdProfile(id).subscribe(data=>{
      this.projects=data;
    })
  }

}
