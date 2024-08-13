import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Job } from '../../model/job';
import { JobServiceService } from '../../service/job-service.service';
import { CompanyServiceService } from '../../service/company-service.service';
import { Company } from '../../model/company';

@Component({
  selector: 'app-job-layout',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './job-layout.component.html',
  styleUrl: './job-layout.component.css'
})
export class JobLayoutComponent implements OnInit {
  idCompany?:number;
  company: Company = new Company();
  job: Job=new Job();
  jobs: Job[] = [];

  constructor(private jobService: JobServiceService, private companyService:CompanyServiceService) { }

  ngOnInit(): void {
    const userCurrentString = localStorage.getItem('idCompany');
    if(userCurrentString){
      this.idCompany = Number(userCurrentString);
      this.getJobsByCompany(this.idCompany);
    }
  }

  getJob(id:number){
    this.jobService.getJobByCompany(id).subscribe(data=>{
      this.jobs=data;
    });
  }

  getJobsByCompany(id:number):void{
    alert("getJobsByCompany "+ id)
    this.jobService.getJobByCompany(id).subscribe(data=>{
      this.jobs=data;
      console.log(this.jobs);
    });
  }

}
