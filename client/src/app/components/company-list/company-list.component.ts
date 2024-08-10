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
  displayedProfiles: Company[] = [];
  company: Company = new Company();
  showCompany: boolean = false;
  profilesPerPage = 3;
  currentPage = 0;

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
      this.updateDisplayedProfiles();
    });
  }

  viewDetailsCompany(id?:number){
    this.companyService.getCompanyById(id).subscribe(data=>{
      if(data){
        this.company = data;
        this.showCompany = true;
        document.body.style.overflowY = 'hidden';
            document.body.style.touchAction = 'none';
      }
    });
  }

  closeViewCompany(){
    this.showCompany = false;
    document.body.style.overflowY = '';
    document.body.style.touchAction = '';
  }

  updateDisplayedProfiles() {
    const startIndex = this.currentPage * this.profilesPerPage;
    this.displayedProfiles = this.companies.slice(startIndex, startIndex + this.profilesPerPage);
  }

  nextPage() {
    if (this.currentPage < this.totalPages() - 1) {
      this.currentPage++;
      this.updateDisplayedProfiles();
    }
  }

  prevPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.updateDisplayedProfiles();
    }
  }

  totalPages() {
    return Math.ceil(this.companies.length / this.profilesPerPage);
  }

}
