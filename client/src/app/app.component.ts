import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { NavigationEnd, Router, RouterLink, RouterOutlet } from '@angular/router';
import { NotificationComponent } from './components/notification/notification.component';
import { AdminLayoutComponent } from './layouts/admin-layout/admin-layout.component';
import { NavbarComponent } from './components/navbar/navbar.component';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,RouterLink,CommonModule,AdminLayoutComponent,NavbarComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'angular';
  showMainNavbar = true;
  showAdminNavbar = false; 



  constructor(private router: Router) {}

  ngOnInit(): void {
    // Sử dụng NavigationEnd để lắng nghe sự kiện thay đổi đường dẫn
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        // Kiểm tra đường dẫn hiện tại
        if (this.router.url.startsWith('/admin')) {
          this.showMainNavbar = false;
          this.showAdminNavbar = true;
        } else if (this.router.url.startsWith('/login') || this.router.url.startsWith('/register')) {
          this.showMainNavbar = false;
          this.showAdminNavbar = false;
        } else {
          this.showMainNavbar = true;
          this.showAdminNavbar = false;
        }
        
      }
    });
  }

  // logout() {
  //   // Xóa thông tin token khỏi localStorage
  //   localStorage.removeItem('authToken');
    
  //   // Chuyển hướng về trang đăng nhập
  //   this.router.navigateByUrl('/login');
  // }

  
}
