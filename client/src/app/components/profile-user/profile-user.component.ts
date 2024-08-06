import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProfileServiceService } from '../../service/profile-service.service';
import { Profile } from '../../model/profile';
import { ProjectListComponent } from "../project-list/project-list.component";
import { ContactComponent } from "../contact/contact.component";

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

  constructor(
    private profileService: ProfileServiceService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const idProfileParam = params.get('id');
      alert(idProfileParam);
      if (idProfileParam !== null) {
        this.idProfile = +idProfileParam; // Convert to number using +
        this.getProfileById(this.idProfile);
      }
    });
  }

  getProfileById(idProfile: number): void {
    this.profileService.getProfileById(idProfile).subscribe(data => {
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
