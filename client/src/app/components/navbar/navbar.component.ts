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

  showNotifications = false;
  toggleNotifications() {
    this.showNotifications = !this.showNotifications;
  }
}
