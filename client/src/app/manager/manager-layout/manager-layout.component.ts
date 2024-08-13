import { Component } from '@angular/core';
import { NavabarComponent } from '../navabar/navabar.component';
import { RouterLink } from '@angular/router';
import { CompanyComponent } from "../company/company.component";

@Component({
  selector: 'app-manager-layout',
  standalone: true,
  imports: [NavabarComponent, RouterLink, CompanyComponent],
  templateUrl: './manager-layout.component.html',
  styleUrl: './manager-layout.component.css'
})
export class ManagerLayoutComponent {

}
