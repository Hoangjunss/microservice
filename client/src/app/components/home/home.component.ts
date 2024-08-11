import { Component, Input, OnInit } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';

import { Profile } from '../../model/profile';
import { ProfileServiceService } from '../../service/profile-service.service';
import { CommonModule } from '@angular/common';
import { ProfileComponent } from '../profile/profile.component';
import { ProfileListComponent } from '../profile-list/profile-list.component';
import { CompanyListComponent } from '../company-list/company-list.component';
import { JobListComponent } from "../job-list/job-list.component";
import { UserServiceService } from '../../service/user-service.service';
import { User } from '../../model/user';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterOutlet, ProfileListComponent, CommonModule, ProfileComponent, RouterModule, CompanyListComponent, JobListComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  idUser?: number;
  profiles: Profile[] = [];
  profile: Profile = new Profile;
  @Input() param?: string;
  constructor(private userService: UserServiceService,private profileService: ProfileServiceService) {

   }
  
   ngOnInit(): void {
    this.getCurrentUser();
  }
  
  getCurrentUser() {
    this.userService.getCurrentUser().subscribe(data => {
      this.idUser = data.id;
      console.log(this.idUser + " user home");
      if (this.idUser !== undefined) {
        this.getProfileByUserId(this.idUser);
      } else {
        console.error('User ID is undefined.');
      }
    });
  }
  
  getProfileByUserId(userId: number): void {
    this.profileService.getProfileByUserId(userId).subscribe(data => {
      this.profile = data;
      if (this.profile?.id !== undefined) {
        localStorage.setItem('idProfileUser', `${this.profile.id}`);
      }
    });
  }
  
  getAll() {
    this.profileService.getProfilesList().subscribe(data => {
      this.profiles = data;
    })
  }

  getByType(type: string) {
    this.profileService.getProfileByType(type).subscribe(data => {
      this.profiles = data;
    })
  }
}
