import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { NavigationEnd, Router, RouterLink, RouterOutlet } from '@angular/router';
import { filter } from 'rxjs';
import { CompanyComponent } from "../company/company.component";
import { JobLayoutComponent } from "../job-layout/job-layout.component";
import { ApplyLayoutComponent } from "../apply-layout/apply-layout.component";
import { HrLayoutComponent } from "../hr-layout/hr-layout.component";
import { Profile } from '../../model/profile';
import { ProfileServiceService } from '../../service/profile-service.service';

@Component({
  selector: 'app-navabar',
  standalone: true,
  imports: [CommonModule, RouterLink, CompanyComponent, JobLayoutComponent, ApplyLayoutComponent, HrLayoutComponent, RouterOutlet],
  templateUrl: './navabar.component.html',
  styleUrl: './navabar.component.css'
})
export class NavabarComponent {
  currentUrl: string = '';
  userCurrent: any;
  profile?: Profile;

  constructor(private router: Router,private profileService: ProfileServiceService) {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      this.currentUrl = this.router.url;
    });
  }
  ngOnInit(): void {
    const userCurrentString = localStorage.getItem('userCurrent');
    if (userCurrentString) {
      this.userCurrent = JSON.parse(userCurrentString);
      console.log('User Current:', this.userCurrent);
      this.getUserProfile();
    }
    this.currentUrl = this.router.url;
    
  }

  isActive(path: string): boolean {
    return this.currentUrl === path;
  }

  logout(): void {
    localStorage.removeItem('userCurrent');
    localStorage.removeItem('authToken');
    this.router.navigate(['/login']);
  }
  getProfileImage(): string {
    if (this.profile?.url) {
      return this.profile.url;
    }
    return 'http://res.cloudinary.com/dgts7tmnb/image/upload/v1723548301/pi41b4rynddelbwfecle.jpg';
  }

  getUserProfile(): void {
    if (this.userCurrent) {
      this.profileService.getProfileByUserId(this.userCurrent.id).subscribe((profile: Profile) => {
        this.profile = profile;
        console.log('Profile:', this.profile);
      });
    }
  }
  
}
