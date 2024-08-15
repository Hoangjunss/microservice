import { CommonModule, JsonPipe } from '@angular/common';
import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { UserServiceService } from '../../service/user-service.service';
import { Router, RouterModule } from '@angular/router';
import { User } from '../../model/user';
import { ProfileServiceService } from '../../service/profile-service.service';
import { Profile } from '../../model/profile';

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
  idProfile : number | undefined;

  constructor(private fb: FormBuilder,private userService : UserServiceService,private router: Router , private profileService: ProfileServiceService) {
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
        const userCurrent = response.user;
        localStorage.setItem('authToken', token);
        localStorage.setItem('userCurrent', JSON.stringify(userCurrent));
        console.log('Login successful, token saved to localStorage');
        console.log("Profile ID response:", response.user.id); 
        this.profileService.getProfileByUserId(response.user.id).subscribe(
          (data) => 
          {
            console.log("Profile ID:", data?.id); 
            if(data!= null)
            {
              this.idProfile = data.id;
              const idProfileString = this.idProfile !== undefined ? this.idProfile.toString() : '';

              localStorage.setItem('idProfile', idProfileString);
            }

            
          }
        )

        console.log('Token:', token);
        if(response.role === 'admin') {
          this.router.navigateByUrl('/admin');
        } else if (response.role === 'hr' || response.role === 'manager'){
          this.router.navigateByUrl('/manager/about');
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
