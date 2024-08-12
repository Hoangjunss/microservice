import { Component } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { NotificationComponent } from '../notification/notification.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterModule,RouterOutlet,NotificationComponent,CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  idProfileNumber:number | undefined;
  constructor (){
    const idProfileUser = localStorage.getItem('idProfileUser');
    this.idProfileNumber = idProfileUser ? Number(idProfileUser) : undefined;
  }

  showNotifications = false;
  toggleNotifications() {
    this.showNotifications = !this.showNotifications;
  }
}
