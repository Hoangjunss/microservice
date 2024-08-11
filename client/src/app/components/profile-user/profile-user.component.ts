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

@Component({
  selector: 'app-profile-user',
  standalone: true,
  imports: [ProjectListComponent, ContactComponent],
  templateUrl: './profile-user.component.html',
  styleUrls: ['./profile-user.component.css']
})
export class ProfileUserComponent implements OnInit {
  currentUrl:string='';
  idProfile: number | null = null;
  idProfileUser?:number;
  profile?: Profile;
  idProfileNumber?:number;

  constructor(
    private profileService: ProfileServiceService,
    private route: ActivatedRoute,
    public router: Router
  ) {
    // Lấy idProfile từ localStorage khi khởi tạo
    const idProfileUser = localStorage.getItem('idProfileUser');
    this.idProfileUser = idProfileUser ? Number(idProfileUser) : undefined;
  }

  ngOnInit(): void {
    const url = this.router.url;
    this.idProfile = this.extractIdFromUrl(url);
    alert(this.idProfile+ " idpf "+ this.idProfileUser+ " idpfu");
    if (this.idProfile != 0 && this.idProfile) {
      this.getProfileById(this.idProfile);
    } else {
      if(this.idProfileUser){
        this.idProfile = this.idProfileUser;
        this.getProfileByUserId(this.idProfile);
      }
    }
  }

  extractIdFromUrl(url: string): number | null {
    const parts = url.split('/');
    const id = Number(parts[parts.length - 1]);
    return isNaN(id) ? null : id;
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
