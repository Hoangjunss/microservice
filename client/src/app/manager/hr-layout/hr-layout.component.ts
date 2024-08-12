import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { UserServiceService } from '../../service/user-service.service';
import { User } from '../../model/user';

@Component({
  selector: 'app-hr-layout',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './hr-layout.component.html',
  styleUrl: './hr-layout.component.css'
})
export class HrLayoutComponent implements OnInit {

  users : User[] = [];

  constructor(private userService : UserServiceService) {}

  ngOnInit() {
    this.getAllUsers();
  }


  getAllUsers(): void {
    this.userService.getAllUser().subscribe(data => {
      this.users = data;
    });
  }
}
