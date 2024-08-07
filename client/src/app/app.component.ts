import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { NotificationComponent } from './components/notification/notification.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,RouterLink,CommonModule,NotificationComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'angular';
  showNavbar = true;

  showNotifications = false;

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.router.events.subscribe((event) => {
      // Lắng nghe sự kiện router để kiểm tra đường dẫn hiện tại
      if (this.router.url === '/user' || this.router.url === '/login' || this.router.url === '/register') {
        this.showNavbar = false;
      } else {
        this.showNavbar = true;
      }
    });
  }

  // logout() {
  //   // Xóa thông tin token khỏi localStorage
  //   localStorage.removeItem('authToken');
    
  //   // Chuyển hướng về trang đăng nhập
  //   this.router.navigateByUrl('/login');
  // }

  toggleNotifications() {
    this.showNotifications = !this.showNotifications;
  }
}
