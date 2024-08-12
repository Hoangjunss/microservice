import { CommonModule, JsonPipe } from '@angular/common';
import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { UserServiceService } from '../../service/user-service.service';
import { Router, RouterModule } from '@angular/router';
import { User } from '../../model/user';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule,FormsModule,ReactiveFormsModule,JsonPipe,RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  user:User = new User();
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
        const token = response.token;

        localStorage.setItem('authToken', token);
        console.log('Login successful, token saved to localStorage');
        console.log('Token:', token);
        if(response.role === 'admin') {
          this.router.navigateByUrl('/admin');
        } else if (response.role === 'hr'){
          this.router.navigateByUrl('/manager');
        } else if(response.role === 'user'){
          this.router.navigateByUrl('/home');
        }
      },
      (error) => {
        console.error('Error logging in:', error);
        // Xóa lỗi trước khi thêm lỗi mới
        this.userForm.get('email')?.setErrors(null);
        this.userForm.get('password')?.setErrors(null);
        
        if (error.error.statusCode === 404) {
          // Thêm lỗi nếu email không tồn tại
          this.userForm.get('email')?.setErrors({ emailNotFound: true });
          console.error('Account not found');
        } 
        else if (error.status === 401) {
          // Thêm lỗi nếu mật khẩu không đúng
          this.userForm.get('password')?.setErrors({ invalidCredentials: true });
          console.error('Invalid email or password');
        }
      }
    );
  
  }
}
