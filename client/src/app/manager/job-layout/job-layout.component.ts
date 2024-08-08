import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Job } from '../../model/job';
import { JobServiceService } from '../../service/job-service.service';

@Component({
  selector: 'app-job-layout',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './job-layout.component.html',
  styleUrl: './job-layout.component.css'
})
export class JobLayoutComponent implements OnInit {
  job: Job=new Job();
  jobs: Job[] = [];

  constructor(private jobService: JobServiceService) { }

  ngOnInit(): void {
    console.log("JobLayoutComponent");
    this.getJob(1545490320);
  }

  getJob(id:number){
    this.jobService.getJobByCompany(id).subscribe(data=>{
      this.jobs=data;
    });
  }

}
