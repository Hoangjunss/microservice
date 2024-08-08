import { Component } from '@angular/core';
import { NavabarComponent } from '../navabar/navabar.component';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-manager-layout',
  standalone: true,
  imports: [NavabarComponent, RouterLink],
  templateUrl: './manager-layout.component.html',
  styleUrl: './manager-layout.component.css'
})
export class ManagerLayoutComponent {

}
