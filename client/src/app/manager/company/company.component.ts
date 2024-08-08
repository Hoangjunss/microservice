import { Component, OnInit } from '@angular/core';
import { CompanyServiceService } from '../../service/company-service.service';
import { Company } from '../../model/company';

@Component({
  selector: 'app-company',
  standalone: true,
  imports: [],
  templateUrl: './company.component.html',
  styleUrl: './company.component.css'
})
export class CompanyComponent implements OnInit {
  company:Company = new Company();

  constructor(private companyService:CompanyServiceService) { }

  ngOnInit(): void {
    
  }

  getCompanies(id:number) {
    this.companyService.getCompanyById(id).subscribe(data=>{
      this.company = data;
    }
    );
  
  }

}
