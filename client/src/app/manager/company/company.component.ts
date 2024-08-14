import { routes } from './../../app.routes';
import { Component, OnInit } from '@angular/core';
import { CompanyServiceService } from '../../service/company-service.service';
import { Company } from '../../model/company';
import { UserServiceService } from '../../service/user-service.service';
import { User } from '../../model/user';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-company',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './company.component.html',
  styleUrl: './company.component.css'
})
export class CompanyComponent implements OnInit {
  company: Company = new Company();
  userCurrent: User = new User();
  isShowingCompany: boolean = false;

  constructor(private companyService: CompanyServiceService) {}

  ngOnInit(): void {
    const userCurrentString = localStorage.getItem('userCurrent');
    if (userCurrentString) {
      this.userCurrent = JSON.parse(userCurrentString);
    }
    if (this.userCurrent.role === 'manager' && this.userCurrent.id) {
      this.getCompanyByManager(this.userCurrent.id);
    }else if(this.userCurrent.role === 'hr' && this.userCurrent.id){
      this.getCompanyByIdHR(this.userCurrent.id);
    }
  }

  getCompanyByIdHR(id:number){
    this.companyService.getCompanyByIddHr(id).subscribe(data => {
      this.company = data;
    });
  }

  getCompanyByManager(idManager: number): void {
    this.companyService.getCompanyByManager(idManager).subscribe(data => {
      this.company = data;
      localStorage.setItem('idCompany', String(this.company.id));
    });
  }

  editCompany(): void {
    this.isShowingCompany = true;
  }

  closeModal(): void {
    this.isShowingCompany = false;
  }

  saveCompany(): void {
    // Logic to save company details
    this.companyService.updateCompany(this.company).subscribe(() => {
      this.isShowingCompany = false;
      alert('Company details updated successfully');
    });
  }
}
