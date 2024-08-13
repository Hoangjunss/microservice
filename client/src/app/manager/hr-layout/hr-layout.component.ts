import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { UserServiceService } from '../../service/user-service.service';
import { User } from '../../model/user';
import { Form, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-hr-layout',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule,FormsModule],
  templateUrl: './hr-layout.component.html',
  styleUrl: './hr-layout.component.css'
})
export class HrLayoutComponent implements OnInit {

  users: User[] = [];
  filteredUsers: User[] = [];
  userForm: FormGroup;
  isModalOpen: boolean = false;
  isEdit: boolean = false;
  modalTitle: string = 'New Employee';
  modalButtonLabel: string = 'Save';
  id: number | undefined;
  searchTerm: string = '';
  searchCriteria: string = 'id';

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
      this.users = data.filter(user => user.role === 'hr');
      this.filteredUsers = this.users;
      console.log(this.filteredUsers)
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
        password: user.password,
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

      }
    }
  }

  saveUser(): void {
    const newUser: User = this.userForm.value;
    newUser.role = 'hr';
    this.userService.signUpUser(newUser).subscribe(data => {
      this.filteredUsers = this.users;
      this.getAllUsers();
      this.closeModal();
      alert("User added successfully");
    }, error => {
      console.error('Lỗi khi đăng ký người dùng:', error);
      if (error.status === 409 || error.error.message === 'Email đã tồn tại') { 
        this.userForm.get('email')?.setErrors({ emailExists: true });
      } 
    });
    console.log(JSON.stringify(newUser));
  }

  updateUser(): void {
    const updatedUser: User = this.userForm.value;
    updatedUser.role = 'hr';
    console.log(JSON.stringify("update:"+updatedUser));
    this.userService.update(updatedUser).subscribe(data => {
      if(data){
        const index = this.users.findIndex(user => user.id === updatedUser.id);
        this.users[index] = updatedUser;
        this.getAllUsers();
        this.filteredUsers = this.users;
        alert("User updated successfully");
      }
      this.closeModal();
    }, error => {
      console.error('Lỗi khi đăng ký người dùng:', error);
      if (error.status === 409 || error.error.message === 'Email đã tồn tại') { 
        this.userForm.get('email')?.setErrors({ emailExists: true });
      } 
    });
    console.log(JSON.stringify("update:"+updatedUser));
  }

  deleteUser(id: number): void {
    const confirmDelete = confirm('Are you sure you want to delete this user?');
  if (confirmDelete) {
    this.userService.deleteUser(id).subscribe(data => {
      this.users = this.users.filter(user => user.id !== id);
      this.getAllUsers();
      this.filteredUsers = this.users;
      alert("User deleted successfully");
    }, error => {
      console.error('Error deleting user:', error);
    });
  } else {
    console.log('User deletion canceled');
  }
  }

  onSearch(): void {
    if(!this.searchTerm){
      this.filteredUsers = this.users;
      return;
    }
    const term = this.searchTerm.toLowerCase();
    this.filteredUsers = this.users.filter(user => {
      if (this.searchCriteria === 'id') {
        return user.id?.toString().toLowerCase().includes(term);
      } else if (this.searchCriteria === 'name') {
        return user.name?.toLowerCase().includes(term);
      } else if (this.searchCriteria === 'email') {
        return user.email?.toLowerCase().includes(term);
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
