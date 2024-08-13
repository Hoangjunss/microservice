import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { filter } from 'rxjs';
import { CompanyComponent } from "../company/company.component";
import { JobLayoutComponent } from "../job-layout/job-layout.component";
import { ApplyLayoutComponent } from "../apply-layout/apply-layout.component";
import { HrLayoutComponent } from "../hr-layout/hr-layout.component";

@Component({
  selector: 'app-navabar',
  standalone: true,
  imports: [CommonModule, RouterLink, CompanyComponent, JobLayoutComponent, ApplyLayoutComponent, HrLayoutComponent],
  templateUrl: './navabar.component.html',
  styleUrl: './navabar.component.css'
})
export class NavabarComponent {
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
