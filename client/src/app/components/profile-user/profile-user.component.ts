import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { ProfileServiceService } from '../../service/profile-service.service';
import { Profile } from '../../model/profile';
import { ProjectListComponent } from "../project-list/project-list.component";
import { ContactComponent } from "../contact/contact.component";
import { HttpClient } from '@angular/common/http';
import { UserServiceService } from '../../service/user-service.service';
import { User } from '../../model/user';
import { filter } from 'rxjs';
import { CreateProfileComponent } from "../create-profile/create-profile.component";
import { CommonModule } from '@angular/common';
import { ImageServiceService } from '../../service/image-service.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-profile-user',
  standalone: true,
  imports: [ProjectListComponent, ContactComponent, CreateProfileComponent, CommonModule,FormsModule,ReactiveFormsModule],
  templateUrl: './profile-user.component.html',
  styleUrls: ['./profile-user.component.css']
})
export class ProfileUserComponent implements OnInit {
  currentUrl: string = '';
  idProfile: number | null = null;
  idProfileUser?: number;
  user: User = new User();
  profile?: Profile;
  idProfileNumber?: number;
  formEditProfile?: boolean;
  selectedFile: File | null = null;
  userCurrent: any;
  

  constructor(
    private userService: UserServiceService,
    private profileService: ProfileServiceService,
    private route: ActivatedRoute,
    public router: Router,
    private imageService: ImageServiceService
  ) {}

  ngOnInit(): void {
    const userCurrentString = localStorage.getItem('userCurrent');
    if (userCurrentString) {
      this.userCurrent = JSON.parse(userCurrentString);
    }
    console.log('User Current String from Local Storage:', userCurrentString);
    const url = this.router.url;
    this.idProfile = this.extractIdFromUrl(url);
    if (this.idProfile != 0 && this.idProfile) {
      this.getProfileById(this.idProfile);
    } else {
      if (userCurrentString) {
        this.getProfileByUserId(this.userCurrent.id);
      }
    }
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  uploadImage() {
    if (this.selectedFile) {
      this.imageService.uploadImage(this.selectedFile).subscribe(data => {
        console.log(data);
        console.log("Image uploaded");
      },error => {
          console.error('Error uploading image:', error);
        }
      );
    }
  }

  getProfileImage(): string {
    if (this.profile?.url) {
      return this.profile.url;
    }
    return 'http://res.cloudinary.com/dgts7tmnb/image/upload/v1723548301/pi41b4rynddelbwfecle.jpg';
  }

  extractIdFromUrl(url: string): number | null {
    const parts = url.split('/');
    const id = Number(parts[parts.length - 1]);
    return isNaN(id) ? null : id;
  }

  getProfileById(idProfile: number): void {
    this.profileService.getProfileById(idProfile).subscribe(data => {
      this.profile = data;
      if (this.profile?.idUser) {
        this.getUserById(this.profile?.idUser);
      }
    });
  }

  getProfileByUserId(userId: number): void {
    this.profileService.getProfileByUserId(userId).subscribe(data => {
      this.profile = data;
      console.log(this.profile + "  PFby id");
      if (this.profile?.idUser) {
        this.getUserById(this.profile?.idUser);
      }
    });
  }

  getUserById(idUser: number) {
    this.userService.getUserById(idUser).subscribe(data => {
      this.user = data;
      console.log(this.user + " by id");
    });
  }

  showEditForm() {
    this.formEditProfile = true;
    console.log(this.profile);
    document.body.style.overflowY = 'hidden';
    document.body.style.touchAction = 'none';
  }

  hideEditForm(): void {
    this.formEditProfile = false;
    document.body.style.overflowY = '';
    document.body.style.touchAction = '';
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

  isCurrentUserProfile(): boolean {
    if (this.profile && this.profile.idUser) {
      return this.profile.idUser === this.userCurrent.id;
    }
    return false;
  }
  isCreateProfile(): boolean
  {
    return !this.profile;
  }
  
}
