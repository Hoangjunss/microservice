import { CommonModule, JsonPipe } from '@angular/common';
import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { UserServiceService } from '../../service/user-service.service';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule,FormsModule,ReactiveFormsModule,JsonPipe,RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  userForm : FormGroup;

  constructor(private fb: FormBuilder,private userService : UserServiceService,private router: Router) {
    this.userForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]], 
    });
  }
  
  submitForm() {
    if (this.userForm.valid) {
      console.log("email :", this.userForm.value);
      this.login();
    }
    else
    {
      console.log("Form is invalid");
    }
  }

  login() {
    this.userService.signInUser(this.userForm.value).subscribe(
      (response: any) => {
        // Giả sử phản hồi từ máy chủ chứa token
        const token = response.token;

        // Lưu token vào localStorage
        localStorage.setItem('authToken', token);

        console.log('Login successful, token saved to localStorage');
        console.log('Token:', token);

        this.router.navigateByUrl('/home');
      },
      (error) => {
        console.error('Error logging in:', error);
      }
    );
  }
}
