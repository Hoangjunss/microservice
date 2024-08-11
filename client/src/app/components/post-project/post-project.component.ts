import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ProjectServiceService } from '../../service/project-service.service';
import { Project } from '../../model/project';
import { title } from 'process';
import { Router } from '@angular/router';

@Component({
  selector: 'app-post-project',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './post-project.component.html',
  styleUrl: './post-project.component.css'
})
export class PostProjectComponent implements OnInit {
  projects:Project[]=[]
  project:Project=new Project;
  projectForm: FormGroup;
  @Input() idUser?:number;
  @Input() projectEdit: Project | null = null;
  @Input() idProfile?: number | null;
  @Output() closeForm = new EventEmitter<void>();
  constructor(private projectService:ProjectServiceService, private fb:FormBuilder, private router: Router){
    this.projectForm = this.fb.group({
      id: [''],
      title: [''],
      description: [''],
      url: [''],
      createAt: [''],
      imageId: [''],
      display: [''],
      imageFile: [null],
      idProfile: [null]
    })
  }
  ngOnInit(): void {
    console.log(this.projectEdit+" selected")
    if(this.projectEdit!=null){
      this.projectForm.patchValue(this.projectEdit)
    }
  }
  createProjectByUser(project:Project){
    this.projectService.createProjectByUser(project).subscribe(data=>{
      this.project=data
    })
  }
  updateProjectByUser(project:Project){
    this.projectService.updateProjectByUser(project).subscribe(data=>{
      this.project=data
    })
  }

  updateProject(event: Event): void {
    event.preventDefault();
    if (this.projectForm.valid) {
      const updatedProject = this.projectForm.value;
      updatedProject.idProfile=this.idProfile;
      console.log(updatedProject + " ID project");
      if(updatedProject?.id != ''){
        this.projectService.updateProjectByUser(updatedProject).subscribe({
        next: (response) => {
          alert('Project updated successfully');
          this.router.navigate(['/list-project']).then(() => {
            window.location.reload();
          });
        },
        error: (error) => {
          console.error('An error occurred:', error);
          alert('An error occurred while updating the project.');
        }
      });
      }else{
        this.projectService.createProjectByUser(updatedProject).subscribe({
          next: (response) => {
            alert('Project created successfully');
            this.router.navigate(['/list-project']).then(() => {
              window.location.reload();
            });
          },
          error: (error) => {
            console.error('An error occurred:', error);
            alert('An error occurred while creating the project.');
          }
        });
      }
      
    } else {
      console.error('Form is invalid');
    }
  }


  onClose() {
    this.closeForm.emit();
  }

}
