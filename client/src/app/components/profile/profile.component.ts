import { Component } from '@angular/core';
import { ContactComponent } from '../contact/contact.component';
import { CreateProfileComponent } from '../create-profile/create-profile.component';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [ContactComponent,CreateProfileComponent,RouterOutlet],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {

}
