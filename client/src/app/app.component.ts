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

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.router.events.subscribe((event) => {
      // Lắng nghe sự kiện router để kiểm tra đường dẫn hiện tại
      if (this.router.url === '/user') {
        this.showNavbar = false;
      } else {
        this.showNavbar = true;
      }
    });
  }
}
