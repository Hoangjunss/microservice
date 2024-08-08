import { Component } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { RouterModule, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ProjectListComponent } from '../project-list/project-list.component';
import { UserListComponent } from '../user-list/user-list.component';

@Component({
  selector: 'app-admin-layout',
  standalone: true,
  imports: [NavbarComponent,RouterOutlet,CommonModule,RouterModule,ProjectListComponent,UserListComponent],
  templateUrl: './admin-layout.component.html',
  styleUrl: './admin-layout.component.css'
})
export class AdminLayoutComponent {

}
