import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { Profile } from '../../model/profile';
import { ProfileServiceService } from '../../service/profile-service.service';
import { Router, RouterOutlet } from '@angular/router';
import { ProfileComponent } from "../profile/profile.component";
import { CreateProfileComponent } from "../create-profile/create-profile.component";

@Component({
  selector: 'app-profile-list',
  standalone: true,
  imports: [CommonModule, RouterOutlet, ProfileComponent, CreateProfileComponent],
  templateUrl: './profile-list.component.html',
  styleUrl: './profile-list.component.css'
})
export class ProfileListComponent implements OnInit {
  profiles: Profile[] = [];
  profile: Profile = new Profile;
  displayedProfiles: Profile[] = [];
  @Input() param?: string;

  profilesPerPage = 3;
  currentPage = 0;
  constructor(private profileService: ProfileServiceService, private router: Router) { }
  
  ngOnInit(): void {
    if (this.param) {
      this.getByType(this.param);
    } else {
      this.getAll();
      
    }
  }

  viewProfile(id?: number): void {
    alert(id);
    if (id) {
      this.router.navigate(['profile/profile-user/', id]);
    }
  }


  getAll() {
    this.profileService.getProfilesList().subscribe(data => {
      this.profiles = data;
      console.log(this.profiles);
      this.updateDisplayedProfiles();
    })
  }
  getByType(type: string) {
    this.profileService.getProfileByType(type).subscribe(data => {
      this.profiles = data;
    })
  }
  getByUser(id: number) {
    this.profileService.getProfileByUser(id).subscribe(data => {
      this.profile = data;
    })
  }

  updateDisplayedProfiles() {
    const startIndex = this.currentPage * this.profilesPerPage;
    this.displayedProfiles = this.profiles.slice(startIndex, startIndex + this.profilesPerPage);
  }

  nextPage() {
    if (this.currentPage < this.totalPages() - 1) {
      this.currentPage++;
      this.updateDisplayedProfiles();
    }
  }

  prevPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.updateDisplayedProfiles();
    }
  }

  totalPages() {
    return Math.ceil(this.profiles.length / this.profilesPerPage);
  }

}
