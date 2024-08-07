import { Component, Input, OnInit } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';

import { Profile } from '../../model/profile';
import { ProfileServiceService } from '../../service/profile-service.service';
import { CommonModule } from '@angular/common';
import { ProfileComponent } from '../profile/profile.component';
import { ProfileListComponent } from '../profile-list/profile-list.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterOutlet,ProfileListComponent,CommonModule,ProfileComponent,RouterModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  profiles: Profile[] = [];
  profile: Profile = new Profile;
  @Input() param?: string;
  constructor(private profileService: ProfileServiceService) { }
  
  ngOnInit(): void {
    if (this.param) {
      this.getByType(this.param);
    } else {
      this.getAll();
    }
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
