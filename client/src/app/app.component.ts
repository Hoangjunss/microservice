import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ProfileListComponent } from './components/profile-list/profile-list.component';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CreateProfileComponent } from './components/create-profile/create-profile.component';
import { FormsModule } from '@angular/forms';
import { ContactComponent } from './components/contact/contact.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,ProfileListComponent,CreateProfileComponent,ContactComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'angular';
}
