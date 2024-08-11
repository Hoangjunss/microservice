import { Component, OnInit } from '@angular/core';
import { ProjectServiceService } from '../../service/project-service.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { User } from '../../model/user';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent implements OnInit{

  users: any[] = [
    {id: 'A01', name: 'Nguyen Van A', email: 'NguyenA@gmail.com', password: '123'},
    {id: 'A02', name: 'Nguyen Van B', email: 'NguyenB@gmail.com', password: '123'},
    {id: 'A03', name: 'Nguyen Van C', email: 'NguyenC@gmail.com', password: '123'},
    {id: 'A04', name: 'Nguyen Van D', email: 'NguyenD@gmail.com', password: '123'},
    {id: 'A05', name: 'Nguyen Van E', email: 'NguyenE@gmail.com', password: '123'}
  ];
  


  filteredUsers: any[] = [...(this.users || [])];
  searchCriteria: string = '1' ;
  searchText: string = '';

  constructor() {}

  ngOnInit(): void {
    console.log("User start");
  }
  

  onSearch(): void {
    if (!this.searchText) {
      this.filteredUsers = [...this.users];
      return;
    }
  
    this.filteredUsers = this.users.filter(user => {
      switch (this.searchCriteria) {
        case '1':
          return user?.id?.toLowerCase().includes(this.searchText.toLowerCase());
        case '2':
          return user?.name?.toLowerCase().includes(this.searchText.toLowerCase());
        case '3': 
          return user?.email?.toLowerCase().includes(this.searchText.toLowerCase());
        default:
          return false;
      }
    });
  }
  

  onCriteriaChange(event: Event): void {
    const target = event.target as HTMLSelectElement; 
    this.searchCriteria = target.value;
    this.onSearch();
  }
  

}
