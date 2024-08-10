import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProfileServiceService } from '../../service/profile-service.service';
import { Profile } from '../../model/profile';
import { ProjectListComponent } from "../project-list/project-list.component";
import { ContactComponent } from "../contact/contact.component";
import { HttpClient } from '@angular/common/http';
import { UserServiceService } from '../../service/user-service.service';
import { User } from '../../model/user';

@Component({
  selector: 'app-profile-user',
  standalone: true,
  imports: [ProjectListComponent, ContactComponent],
  templateUrl: './profile-user.component.html',
  styleUrls: ['./profile-user.component.css']
})
export class ProfileUserComponent implements OnInit {
  idProfile?: number;
  profile?: Profile;
  user:User = new User();

  constructor(
    private userService: UserServiceService,
    private profileService: ProfileServiceService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    alert("Profile-user")
    this.route.paramMap.subscribe(params => {
      const idProfileParam = params.get('id');
      if (idProfileParam !== null) {
        this.idProfile = +idProfileParam; // Convert to number using +
        this.getProfileById(this.idProfile);
      }else{
        this.getCurrentUser();
        if (this.user?.id !== undefined) {
          this.getProfileByUserId(this.user.id);
          if (this.profile?.id !== undefined) {
            localStorage.setItem('idProfileUser', `${this.profile.id}`);
          }
        } else {
          console.error('User ID is undefined.');
        }
      }
    });
  }

  getCurrentUser(){
    this.userService.getCurrentUser().subscribe(data=>{
      this.user = data;
    })
  }

  getProfileById(idProfile: number): void {
    this.profileService.getProfileById(idProfile).subscribe(data => {
      this.profile = data;
    });
  }

  getProfileByUserId(userId: number): void {
    this.profileService.getProfileByUserId(userId).subscribe(data => {
      this.profile = data;
    });
  }


  scrollToSection(sectionId: string): void {
    const section = document.getElementById(sectionId);
    if (section) {
        const navbarHeight = 50;
        const sectionPosition = section.getBoundingClientRect().top + window.pageYOffset;
        
        window.scrollTo({
            top: sectionPosition - navbarHeight,
            behavior: 'smooth'
        });
    }
}
}
