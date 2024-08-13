import { Component, OnInit } from '@angular/core';
import { Router, RouterModule, RouterOutlet } from '@angular/router';
import { NotificationComponent } from '../notification/notification.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterModule,RouterOutlet,NotificationComponent,CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {
  idProfileNumber:number | undefined;
  userCurrent: any;
  constructor (private router: Router){
    const idProfileUser = localStorage.getItem('idProfileUser');
    this.idProfileNumber = idProfileUser ? Number(idProfileUser) : undefined;
  }
  ngOnInit(): void {
    this.userCurrent = localStorage.getItem('userCurrent');
  }

  showNotifications = false;
  toggleNotifications() {
    this.showNotifications = !this.showNotifications;
  }

  logout(): void {
    localStorage.removeItem('userCurrent');
    this.router.navigate(['/login']);
  }
  
}
