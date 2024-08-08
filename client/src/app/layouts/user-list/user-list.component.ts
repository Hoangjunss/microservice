import { Component, OnInit } from '@angular/core';
import { ProjectServiceService } from '../../service/project-service.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent implements OnInit{

  users:any[]=[
    {id:'A01', name:'Nguyen Van A', email:'NguyenA@gmail.com', password:'123'},
    {id:'A02', name:'Nguyen Van B', email:'NguyenB@gmail.com', password:'123'},
    {id:'A03', name:'Nguyen Van C', email:'NguyenC@gmail.com', password:'123'},
    {id:'A04', name:'Nguyen Van D', email:'NguyenD@gmail.com', password:'123'},
    {id:'A05', name:'Nguyen Van E', email:'NguyenE@gmail.com', password:'123'},

  ];
  constructor() {}

  ngOnInit(): void {
    console.log("User start");
  }

}
