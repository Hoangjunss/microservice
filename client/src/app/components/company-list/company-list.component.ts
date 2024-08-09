import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { Company } from '../../model/company';
import { CompanyServiceService } from '../../service/company-service.service';

@Component({
  selector: 'app-company-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './company-list.component.html',
  styleUrl: './company-list.component.css'
})
export class CompanyListComponent implements OnInit{
  companies: Company[]=[];
  company: Company = new Company();
  @Input() typeCompany?: string;
  constructor(private companyService: CompanyServiceService) { }

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

}
