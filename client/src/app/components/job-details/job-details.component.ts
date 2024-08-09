import { Component, Input, OnInit } from '@angular/core';
import { Company } from '../../model/company';
import { CommonModule } from '@angular/common';
import { Job } from '../../model/job';
import { JobServiceService } from '../../service/job-service.service';
import { ActivatedRoute } from '@angular/router';
import { CompanyServiceService } from '../../service/company-service.service';

@Component({
  selector: 'app-job-details',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './job-details.component.html',
  styleUrl: './job-details.component.css'
})
export class JobDetailsComponent implements OnInit {
  company: Company | undefined;
  job: Job | undefined;

  constructor(private jobSerivce: JobServiceService, private companyService: CompanyServiceService, private route: ActivatedRoute) { }
  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = Number(params.get('id'));
      if (id) {
        this.loadJobDetails(id);
      }
    });
  }
  loadJobDetails(id: number): void {
    this.jobSerivce.getJobById(id).subscribe(job => {
      this.job = job;
      this.getCompanyByJob(job.idCompany);
    });
  }

  getCompanyByJob(id?:number){
    this.companyService.getCompanyById(id).subscribe(company => {
      this.company = company;
    });
  }

}