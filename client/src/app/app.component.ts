import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ProfileListComponent } from './profile-list/profile-list.component';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,ProfileListComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'angular';
}
