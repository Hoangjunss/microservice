import { CommonModule, JsonPipe } from '@angular/common';
import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { UserServiceService } from '../../service/user-service.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule,FormsModule,ReactiveFormsModule,JsonPipe],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  userForm : FormGroup;

  constructor(private fb: FormBuilder,private userService : UserServiceService) {
    this.userForm = this.fb.group({
      userName: ['', [Validators.required]],
      password: ['', [Validators.required]], 
    });
  }
  
  submitForm() {
    if (this.userForm.valid) {
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
      },
      (error) => {
        console.error('Error logging in:', error);
      }
    );
  }
}
