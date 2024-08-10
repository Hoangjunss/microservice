import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { Company } from '../../model/company';
import { CompanyServiceService } from '../../service/company-service.service';
import { JobListComponent } from "../job-list/job-list.component";
import { Router } from '@angular/router';

@Component({
  selector: 'app-company-list',
  standalone: true,
  imports: [CommonModule, JobListComponent],
  templateUrl: './company-list.component.html',
  styleUrl: './company-list.component.css'
})
export class CompanyListComponent implements OnInit{
  companies: Company[]=[];
  company: Company = new Company();
  showCompany: boolean = false;

  @Input() typeCompany?: string;
  constructor(private companyService: CompanyServiceService, public router: Router) { }

  ngOnInit(): void {
    if (this.typeCompany) {

    }else{
      this.getAllCompany();
    }
    
  }

  getAllCompany(){
    this.companyService.getAllCompanies().subscribe(data => {
      this.companies = data;
    });
  }

  viewDetailsCompany(id?:number){
    this.companyService.getCompanyById(id).subscribe(data=>{
      if(data){
        this.company = data;
        this.showCompany = true;
      }
    });
  }

  closeViewCompany(){
    this.showCompany = false;
  }

}
