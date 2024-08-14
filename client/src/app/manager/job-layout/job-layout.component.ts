import { CommonModule } from '@angular/common';
import { Component, model, OnInit } from '@angular/core';
import { Job } from '../../model/job';
import { JobServiceService } from '../../service/job-service.service';
import { CompanyServiceService } from '../../service/company-service.service';
import { Company } from '../../model/company';
import { ProfileServiceService } from '../../service/profile-service.service';
import { Profile } from '../../model/profile';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-job-layout',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './job-layout.component.html',
  styleUrl: './job-layout.component.css'
})
export class JobLayoutComponent implements OnInit {
  idCompany?:number;
  company: Company = new Company();
  idJob?:number;
  job: Job=new Job();
  jobs: Job[] = [];
  filteredUsers: Job[] = [];
  profilePending?: Profile[] = [];
  profile: Profile = new Profile();
  jobForm: FormGroup;
  showJob: boolean = false;
  option: boolean = false;
  showEditJob: boolean = false;
  isEditMode = false;
  title: string = '';
  question: string = '';
  searchTerm: string = '';
  searchCriteria: string = 'typeJob';

  constructor(private jobService: JobServiceService, private profileService:ProfileServiceService, private router:Router, private fb:FormBuilder) {
    this.jobForm = this.fb.group({
      id: [],
      title: [''],
      description: [''],
      typeJob: [''],
      size: [''],
      idProfiePending: [],
      idProfile: [],
      idCompany: [this.idCompany],
    })
  }

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
    this.jobService.getJobByCompany(id).subscribe(data=>{
      this.jobs=data;
      this.filteredUsers = this.jobs;
      console.log(this.jobs);
    });
  }

  getProfileByJobPending(id:number[]){
    this.profileService.getListProfileByIdPendingJob(id).subscribe(data=>{
      this.profilePending=data;
      console.log(this.profilePending);
    });
  }

  viewDetailsJob(job:Job){
    console.log(job+" View Details Job");
    this.job = job;
    console.log("this.job.idProfilePending "+ this.job);
    if(this.job.idProfiePending){
      this.getProfileByJobPending(this.job.idProfiePending);
    }
    this.showJob = true;
    document.body.style.overflowY = 'hidden';
    document.body.style.touchAction = 'none';
  }

  closeViewJob(){
    this.profilePending = [];
    this.job = new Job();
    this.showJob = false;
    document.body.style.overflowY = '';
    document.body.style.touchAction = '';
  }

  openModal(title: string, question: string, idJob?:number){
    this.title = title;
    this.question = question;
    this.option = true;
    if(idJob){
      this.idJob = idJob;
    }
  }

  onOk() {
    this.option = false;
    alert("DElete" + this.idJob);
    if(this.idJob){
      this.deleteJob(this.idJob);
    }
  }

  onCancel(){
    this.option = false;
  }

  deleteJob(id:number){
    if(id){
      this.jobService.deleteJob(id);
    } 
  }

  acceptProfile(idProfile:number | undefined, idJob:number | undefined, size:number | undefined){
    if(idProfile && idJob){
      if(size && size>0){
        this.jobService.acceptProfileJob(idJob, idProfile).subscribe(data => {
        if(data){
          alert("Profile accepted successfully");
        }
      })
      }else{
        alert("Job full");
      }
    }
  }

  rejectProfile(idProfile:number | undefined, idJob:number | undefined){
    if(idProfile&&idJob){
      this.jobService.rejectProfileJob(idJob, idProfile).subscribe(data => {
        if(data){
          alert("Profile rejected successfully");
        }
      })
    }
  }

  viewProfile(id?: number): void {
    alert(id);
    if (id) {
      this.router.navigate(['manager/profile/profile-user/', id]);
    }
  }

  createJob() {
    this.isEditMode = false;
    this.showEditJob = true;
    this.jobForm.reset();
  }

  editJob(job: Job) {
    this.isEditMode = true;
    this.showEditJob = true;
    this.jobForm.patchValue({
      id: job.id,
      title: job.title,
      description: job.description,
      typeJob: job.typeJob,
      size: job.size,
      idProfiePending: job.idProfiePending,
      idProfile: job.idProfile,
      idCompany: job.idCompany,
    });
  }

  onSubmit(event:Event) {
    event.preventDefault();
    alert("Submit");
    if (this.jobForm.valid) {
      const jobData: Job = this.jobForm.value;
      if (this.isEditMode) {
        this.updateJob(jobData);
      } else {
        this.createNewJob(jobData);
      }
      this.showEditJob = false;
    }
  }

  private createNewJob(jobData: Job) {
    console.log('Creating new job:', jobData);
    if(jobData){
      jobData.idCompany = this.idCompany;
      this.jobService.createJob(jobData).subscribe(() => {
        alert('Job created successfully');
        window.location.reload();
      });
    }
  }

  private updateJob(jobData: Job) {
    console.log('Updating job:', jobData);
    this.jobService.updateJob(jobData).subscribe(() => {
      alert('Job updated successfully');
      window.location.reload();
    });
  }

  onClose() {
    this.showEditJob = false;
  }

  onSearch(): void {
    if (!this.searchTerm) {
      this.filteredUsers = this.jobs;
      return;
    }
    const term = this.searchTerm.toLowerCase();
    this.filteredUsers = this.jobs.filter(job => {
      if (this.searchCriteria === 'typeJob') {
        return job.typeJob?.toString().toLowerCase().includes(term);
      } else if (this.searchCriteria === 'title') {
        return job.title?.toLowerCase().includes(term);
      }
      return false;
    });
  }

  onCriteriaChange(event: Event): void {
    const target = event.target as HTMLSelectElement;
    this.searchCriteria = target.value;
    this.onSearch();
  }

}
