import { Component, OnInit } from '@angular/core';
import { ProjectServiceService } from '../../service/project-service.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { User } from '../../model/user';
import { UserServiceService } from '../../service/user-service.service';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent implements OnInit{

  users: User[]=[];
  


  filteredUsers: any[] = [...(this.users || [])];
  searchCriteria: string = '1' ;
  searchText: string = '';

  constructor(private userService: UserServiceService) {}

  ngOnInit(): void {
    console.log("User start");
    this.getAllUsers();
  }

  blockUser(user: User): void {
    if (window.confirm(`Bạn có chắc chắn muốn block user ${user.name}?`)) {
      user.active = false;
      this.userService.updateUser(user).subscribe(data => {
        console.log(data);
      });
    }
  }
  
  unlockUser(user: User): void {
    if (window.confirm(`Bạn có chắc chắn muốn unlock user ${user.name}?`)) {
      user.active = true;
      this.userService.updateUser(user).subscribe(data => {
        console.log(data);
      });
    }
  }
  
  

  onSearch(): void {
    if (!this.searchText) {
      this.filteredUsers = [...this.users];
      return;
    }
  
    const searchTextLower = this.searchText.toLowerCase();
  
    this.filteredUsers = this.users.filter(user => {
      switch (this.searchCriteria) {
        case '1': // Tìm kiếm theo ID
          return user?.id?.toString().toLowerCase().includes(searchTextLower);
        case '2': // Tìm kiếm theo tên
          return user?.name?.toLowerCase().includes(searchTextLower);
        case '3': // Tìm kiếm theo email
          return user?.email?.toLowerCase().includes(searchTextLower);
        case '4': // Tìm kiếm theo trạng thái hoạt động (active)
          if(this.searchText.toLowerCase() === 'block'){
            return user?.active
          }else if(this.searchText.toLowerCase() === 'unblock'){
            return !user?.active
          }
          return false;
        default:
          return false;
      }
    });
  }
  

  getAllUsers(): void {
    this.userService.getAllUser().subscribe(users => {
      this.users = users.filter(users=>users.role === 'user');
      this.filteredUsers = [...this.users]; // Cập nhật filteredUsers ngay sau khi nhận dữ liệu
    });
  }
  

  onCriteriaChange(event: Event): void {
    const target = event.target as HTMLSelectElement; 
    this.searchCriteria = target.value;
    this.onSearch();
  }
  

}
