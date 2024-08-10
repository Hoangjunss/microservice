import { UserServiceService } from './../../service/user-service.service';
import { CommonModule, NumberSymbol } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { Job } from '../../model/job';
import { JobServiceService } from '../../service/job-service.service';
import { Router, RouterOutlet } from '@angular/router';
import { User } from '../../model/user';

@Component({
  selector: 'app-job-list',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  templateUrl: './job-list.component.html',
  styleUrl: './job-list.component.css'
})
export class JobListComponent implements OnInit {
  job: Job = new Job();
  jobs: Job[] = [];
  @Input() user: User= new User();
  @Input() idCompany!: number | undefined ;
  @Input() isListJob: boolean = false;
  constructor (private userService: UserServiceService, private jobService:JobServiceService, private router: Router){
    const idProfileUser = localStorage.getItem('idProfileUser');
    const idProfileNumber = idProfileUser ? Number(idProfileUser) : undefined;
   }

  ngOnInit(): void {
    if(this.idCompany){
      this.getJobsByIdCompany(this.idCompany);
    }else{
      this.getJobs();
    }
  }

  getJobs(): void {
    this.jobService.getAllJobs().subscribe(data => {
      this.jobs = data;
    });
  }

  getJobsByIdCompany(id:number): void{
    this.jobService.getJobByCompany(id).subscribe(data=>{
      this.jobs=data;
    })
  }

  viewJobDetails(job:Job):void{
    if (job.id) {
      this.jobService.setJob(job);
      this.router.navigate(['job-details/', job.id]);
    }
  }
}
