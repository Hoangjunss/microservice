import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { UserServiceService } from '../../service/user-service.service';
import { User } from '../../model/user';
import { Form, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-hr-layout',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './hr-layout.component.html',
  styleUrl: './hr-layout.component.css'
})
export class HrLayoutComponent implements OnInit {

  users: User[] = [];
  userForm: FormGroup;
  isModalOpen: boolean = false;
  isEdit: boolean = false;
  modalTitle: string = 'New Employee';
  modalButtonLabel: string = 'Save';

  constructor(private userService: UserServiceService, private fb: FormBuilder) {
    this.userForm = this.fb.group({
      id: ['', Validators.required],
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }

  ngOnInit() {
    this.getAllUsers();
  }


  getAllUsers(): void {
    this.userService.getAllUser().subscribe(data => {
      this.users = data;
    });
  }

  openModal(action: 'add' | 'edit', user?: User): void {
    this.isModalOpen = true;
    this.isEdit = action === 'edit';
    this.modalTitle = this.isEdit ? 'Edit Employee' : 'New Employee';
    this.modalButtonLabel = this.isEdit ? 'Update' : 'Save';

    if (this.isEdit && user) {
      this.userForm.patchValue({
        id: user.id,
        name: user.name,
        email: user.email,
        password: '',
      });
    } else {
      this.userForm.reset();
    }
  }

  closeModal(): void {
    this.isModalOpen = false;
  }

  onSubmit(): void {
    if (this.userForm.valid) {
      if (this.isEdit) {
        this.updateUser();
      } else {
        this.saveUser();
        alert("User added successfully");
      }
    }
  }

  saveUser(): void {
    const newUser: User = this.userForm.value;
    newUser.role = 'hr';
    this.userService.signUpUser(newUser).subscribe(data => {
      this.users.push(data);
      this.closeModal();
    }, error => {
      console.error('Error saving user:', error);
    });
    console.log(JSON.stringify(newUser));
  }

  updateUser(): void {
    const updatedUser: User = this.userForm.value;
    console.log(JSON.stringify("update:"+updatedUser));
    alert(updatedUser.id);
    this.userService.update(updatedUser).subscribe(data => {
      if(data){
        const index = this.users.findIndex(user => user.id === updatedUser.id);
        this.users[index] = updatedUser;
        alert("User updated successfully");
      }

      this.closeModal();
    }, error => {
      console.error('Error updating user:', error);
    });
    console.log(JSON.stringify("update:"+updatedUser));
  }




}
