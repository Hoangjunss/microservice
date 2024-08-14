import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { User } from '../../model/user';
import { UserServiceService } from '../../service/user-service.service';
import { Router } from '@angular/router';
import { JobServiceService } from '../../service/job-service.service';
import { Job } from '../../model/job';
import { ProfileServiceService } from '../../service/profile-service.service';
import { Profile } from '../../model/profile';

@Component({
  selector: 'app-emloyee-layout',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './emloyee-layout.component.html',
  styleUrl: './emloyee-layout.component.css'
})
export class EmloyeeLayoutComponent implements OnInit {
  profile: Profile = new Profile();
  idCompany?: number;
  jobs: Job[] = [];
  jobUsersMap: Map<number, User[]> = new Map(); // Map jobId to list of Users

  constructor(
    private userService: UserServiceService, 
    private router: Router, 
    private jobService: JobServiceService,
    private profileService: ProfileServiceService
  ) {}

  ngOnInit(): void {
    const userCurrentString = localStorage.getItem('idCompany');
    if (userCurrentString) {
      this.idCompany = Number(userCurrentString);
      this.getJobsByCompany(this.idCompany);
    }
  }

  getJobsByCompany(id: number): void {
    this.jobService.getJobByCompany(id).subscribe(jobs => {
      this.jobs = jobs;
      this.jobs.forEach(job => {
        if (job.idProfile && job.idProfile.length > 0) {
          console.log("(job.idProfile && job.idProfile.length > 0 "+ job.idProfile);
          this.getProfileById(job.id!, job.idProfile);
        }
      });
    });
  }

  getProfileById(jobId: number, profileIds: number[]): void {
    this.profileService.getListProfileByIdPendingJob(profileIds).subscribe(profiles => {
      profiles.forEach(profile => {
        if (profile.idUser) {
          console.log("(profile.idUser) " + profile.idUser);
          this.getUserById(jobId, profile.idUser);
        }
      });
    });
  }

  getUserById(jobId: number, userId: number): void {
    this.userService.getUserById(userId).subscribe(user => {
      let usersForJob = this.jobUsersMap.get(jobId);
      console.log("usersForJob "+ usersForJob);
      if (!usersForJob) {
        usersForJob = [];
        this.jobUsersMap.set(jobId, usersForJob);
      }
      usersForJob.push(user);
    });
  }

  deleteUser(id: number): void {
    this.userService.deleteUser(id).subscribe(() => {
      alert('User deleted successfully');
      if (this.idCompany) {
        this.getJobsByCompany(this.idCompany);
      }
    });
  }

  openModal(action: string, user: User): void {
    // Handle modal open logic
  }
}
