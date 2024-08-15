import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ProfileServiceService } from '../../service/profile-service.service';
import { Profile } from '../../model/profile';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-profile',
  standalone: true,
  imports: [FormsModule, CommonModule, ReactiveFormsModule],
  templateUrl: './create-profile.component.html',
  styleUrl: './create-profile.component.css'
})
export class CreateProfileComponent implements OnInit {
  profileForm: FormGroup;
  profileEdit : Profile = new Profile();
  selectedFile: File | null = null;

  @Input() profile: Profile | undefined;
  @Input() idProfile?: number | null;
  @Output() closeForm = new EventEmitter<void>();

  constructor(private fb: FormBuilder, private profileService: ProfileServiceService) {
    this.profileForm = this.fb.group({
      id: [''],
      title: ['', Validators.required],
      objective: [''],
      education: [''],
      workExperience: [''],
      skills: [''],
      typeProfile: [''],
      url: [''],
      idUser: this.profile,
      contact: this.fb.group({
        address: [''],
        phone: [''],
        email: ['']
      }),
      imageFile: [null]
    });
  }

  ngOnInit(): void {
    alert(this.profile);
    if (this.profile) {
      this.profileForm.patchValue(this.profile);
      if (this.profile.contact) {
        this.profileForm.get('contact')?.patchValue(this.profile.contact);
      }
    }
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement; 
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    }
  }

  updateProject(): void {
    if (this.profileForm.valid) {
      const formData: FormData = new FormData();
  
      const profileData = this.profileForm.value;
      for (const key in profileData) {
        if (profileData.hasOwnProperty(key) && key !== 'contact' && key !== 'imageFile') {
          formData.append(key, profileData[key]);
        }
      }
  
      if (profileData.contact) {
        for (const contactKey in profileData.contact) {
          if (profileData.contact.hasOwnProperty(contactKey)) {
            formData.append(`contact.${contactKey}`, profileData.contact[contactKey]);
          }
        }
      }
  
      const imageFile = this.profileForm.get('imageFile')?.value;
      if (this.selectedFile) {
        formData.append('image', this.selectedFile);
      } 

      console.log('FormData entries:');
      formData.forEach((value, key) => {
        console.log(`${key}: ${value}`);
      });
      console.log('Profile form', this.profileForm.value);
      this.profileService.updateProfile(formData).subscribe(response => {
        console.log('Profile updated successfully', response);
        alert('Profile updated successfully')
        window.location.reload();
      }, error => {
        console.error('Error updating profile', error);
      });
    }
  }

  getProfileImage(): string {
    return this.profile?.url ? this.profile.url : 'http://res.cloudinary.com/dgts7tmnb/image/upload/v1723548301/pi41b4rynddelbwfecle.jpg';
  }

  onClose(): void {
    this.closeForm.emit();
  }
}
