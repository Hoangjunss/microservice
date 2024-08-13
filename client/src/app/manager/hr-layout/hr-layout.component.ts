import { Company } from './../../model/company';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { UserServiceService } from '../../service/user-service.service';
import { User } from '../../model/user';
import { Form, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CompanyServiceService } from '../../service/company-service.service';

@Component({
  selector: 'app-hr-layout',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './hr-layout.component.html',
  styleUrl: './hr-layout.component.css'
})
export class HrLayoutComponent implements OnInit {

  company:Company = new Company();
  idCompany?:number;
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

  constructor(private userService: UserServiceService, private fb: FormBuilder, private companyService: CompanyServiceService) {
    this.userForm = this.fb.group({
      id: [''],
      idEmployee: ['', Validators.required],
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }

  ngOnInit() {
    const userCurrentString = localStorage.getItem('idCompany');
    if(userCurrentString){
      this.idCompany = Number(userCurrentString);
    }
    if(this.idCompany){
      this.getCompanyById(this.idCompany);
    }
  }

  getCompanyById(id:number){
    this.companyService.getCompanyById(id).subscribe(data => {
      this.company = data;
      if(this.company.idHR){
        this.getListHrByIdCompany(this.company.idHR);
      }
    });
  }

  getListHrByIdCompany(id:number[]){
    this.userService.getListUserById(id).subscribe(data => {
      this.users = data;
      this.filteredUsers = this.users;
      console.log(this.filteredUsers)
    });
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
        idEmployee: user.idEmployee,
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
    } else {
      alert("Please fill out all required fields correctly.");
    }
  }
  

  saveUser(): void {
    alert('save user');
    alert(this.idCompany+ "idCompany")
    const newUser: User = this.userForm.value;
    newUser.role = 'hr';
    // Kiểm tra xem có trường dữ liệu nào còn trống không
    if (!newUser.idEmployee || !newUser.name || !newUser.email || !newUser.password) {
      alert("Please fill out all required fields!!!");
      return;
    }
    if(this.idCompany){
        this.companyService.setHrToCompany(newUser, this.idCompany).subscribe(data => {
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
    
  }

  updateUser(): void {
    // Lấy dữ liệu từ form
    const updatedUser: User = this.userForm.value;
    updatedUser.role = 'hr';
  
    // Tìm dữ liệu hiện tại của người dùng
    const currentUser = this.users.find(user => user.idEmployee === this.userForm.get('idEmployee')?.value);
    console.log(JSON.stringify(this.users));
    console.log(JSON.stringify(updatedUser) + " from JSON");
    console.log(this.users[0].idEmployee+" idEmployee "+ this.userForm.get('idEmployee')?.value);
    if (!currentUser) {
      alert('User not found');
      return;
    }

    if(currentUser.id === updatedUser.id && currentUser.email === updatedUser.email && currentUser.name === updatedUser.name && currentUser.password === updatedUser.password){
      alert("Nothing to update!!!");
      return;
    }

  
    // Kiểm tra xem có trường dữ liệu nào còn trống không
    if (!updatedUser.id || !updatedUser.name || !updatedUser.email || !updatedUser.password) {
      alert("Please fill out all required fields!!!");
      return;
    }
  
    this.userService.update(updatedUser).subscribe(data => {
      if (data) {
        const index = this.users.findIndex(user => user.id === updatedUser.id);
        if (index !== -1) {
          // Cập nhật danh sách người dùng
          this.users[index] = updatedUser;
          this.getAllUsers(); // Tải lại danh sách người dùng
          this.filteredUsers = this.users;
          alert("User updated successfully");
        }
      }
      this.closeModal();
    }, error => {
      console.error('Error updating user:', error);
      if (error.status === 409 || error.error.message === 'Email already exists') {
        this.userForm.get('email')?.setErrors({ emailExists: true });
      }
    });
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
    if (!this.searchTerm) {
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
