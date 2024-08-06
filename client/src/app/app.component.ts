import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,RouterLink,CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'angular';
  showNavbar = true;
  userName: string | null = null;

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
    this.userName = localStorage.getItem('username');
  }

  logout() {
    // Xóa thông tin người dùng và token khỏi localStorage
    localStorage.removeItem('authToken');
    localStorage.removeItem('userName');
    
    // Chuyển hướng về trang đăng nhập
    this.router.navigateByUrl('/login');
  }
}
