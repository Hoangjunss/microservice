import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ProfileServiceService } from '../profile-service.service';
import { Profile } from '../profile';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-create-profile',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './create-profile.component.html',
  styleUrl: './create-profile.component.css'
})
export class CreateProfileComponent implements OnInit {
  profile:Profile=new Profile()
  constructor(private profileService:ProfileServiceService){}
  ngOnInit(): void {
    
  }
  sendProfile(){
    console.log("send");
    this.profileService.createProfile(this.profile);
  }

}
