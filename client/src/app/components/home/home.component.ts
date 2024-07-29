import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ProfileListComponent } from '../profile-list/profile-list.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterOutlet,ProfileListComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

}
