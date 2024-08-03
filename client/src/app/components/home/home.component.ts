import { Component, Input, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ProfileListComponent } from '../profile-list/profile-list.component';
import { Profile } from '../../model/profile';
import { ProfileServiceService } from '../../service/profile-service.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterOutlet,ProfileListComponent,CommonModule],
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
