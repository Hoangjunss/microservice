import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Profile } from '../profile';
import { ProfileServiceService } from '../profile-service.service';

@Component({
  selector: 'app-profile-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './profile-list.component.html',
  styleUrl: './profile-list.component.css'
})
export class ProfileListComponent implements OnInit{
  profiles?:Profile[]
  constructor(private profileService:ProfileServiceService){}
  ngOnInit(): void {
    this.getAll();
  }
  getAll(){
    this.profileService.getProfilesList().subscribe(data=>{
      this.profiles=data;
    })
  }

}
