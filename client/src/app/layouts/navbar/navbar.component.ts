import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router, RouterLink, RouterModule } from '@angular/router';
import { ProjectListComponent } from '../project-list/project-list.component';
import { filter } from 'rxjs';
import { UserListComponent } from '../user-list/user-list.component';
import { CompanyListComponent } from '../company-list/company-list.component';
import { LoginComponent } from '../../components/login/login.component';


@Component({
  selector: 'app-navbar-admin',
  standalone: true,
  imports: [RouterModule,LoginComponent,CommonModule,ProjectListComponent,RouterLink,UserListComponent,CompanyListComponent],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {
  currentUrl: string = '';

  constructor(private router: Router) {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      this.currentUrl = this.router.url;
    });
  }
  ngOnInit(): void {
    this.currentUrl = this.router.url;
  }

  isActive(path: string): boolean {
    return this.currentUrl === path;
  }

  logout(): void {
    localStorage.removeItem('authToken');
    this.router.navigate(['/login']);
  }
}
