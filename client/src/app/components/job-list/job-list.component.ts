import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { Job } from '../../model/job';
import { JobServiceService } from '../../service/job-service.service';

@Component({
  selector: 'app-job-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './job-list.component.html',
  styleUrl: './job-list.component.css'
})
export class JobListComponent implements OnInit {
  job: Job = new Job();
  jobs: Job[] = [];
  @Input() idCompany?: string;
  constructor (private jobService:JobServiceService){ }

  ngOnInit(): void {
    if(this.idCompany){

    }else{
      this.getJobs();
    }
  }

  getJobs(): void {
    this.jobService.getAllJobs().subscribe(data => {
      this.jobs = data;
    });
  }

}
