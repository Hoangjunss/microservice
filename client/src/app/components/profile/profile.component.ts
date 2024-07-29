import { Component } from '@angular/core';
import { ContactComponent } from '../contact/contact.component';
import { CreateProfileComponent } from '../create-profile/create-profile.component';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [ContactComponent,CreateProfileComponent],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {

}
