import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ProfileListComponent } from './profile-list/profile-list.component';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CreateProfileComponent } from './create-profile/create-profile.component';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,ProfileListComponent,CreateProfileComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'angular';
}
