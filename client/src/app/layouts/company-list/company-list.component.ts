import { Component, OnInit } from '@angular/core';
import { CompanyServiceService } from '../../service/company-service.service';
import { Company } from '../../model/company';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { User } from '../../model/user';
import { UserServiceService } from '../../service/user-service.service';

@Component({
  selector: 'app-company-list',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './company-list.component.html',
  styleUrls: ['./company-list.component.css']
})
export class CompanyListComponent implements OnInit {
  isShowingCompany: boolean = false;
  isModalOpen: boolean = false;
  companyForm!: FormGroup;
  userForm: FormGroup;
  listCompany: Company[] = [];
  isEdit: boolean = false;
  idCompany?: number;
  modalTitle: string = '';
  modalButtonLabel: string = '';
  company?: Company;
  manager?: User | undefined;
  managers: { [key: number]: User } = {};
  

  constructor(
    private companyService: CompanyServiceService,
    private formBuilder: FormBuilder,
    private userService:UserServiceService
  ) {
    this.userForm = this.formBuilder.group({
      id: [''],
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    console.log("Company start");
    this.initForm();
    this.getAllCompany();
  }

  initForm(): void {
    this.companyForm = this.formBuilder.group({
      name: ['', Validators.required],
      type: ['', Validators.required],
      description: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required, Validators.pattern(/^\+?\d{10,}$/)]]
    });
  }

  getAllCompany(): void {
    this.companyService.getAllCompanies().subscribe(data => {
      console.log(data);
      this.listCompany = data;
      this.listCompany.forEach(company => {
        if (company.idManager) {
          this.getManagerById(company.idManager);
        }
      });
    });
  }

  getManagerById(id: number): void {
    this.userService.getUserById(id).subscribe(data => {
      this.managers[id] = data;
      console.log(this.managers[id] + "  Managers");
      console.log(this.managers[id].email + " Managers");
    });
  }



  getAllManager(){

  }

  editCompany(company?: Company): void {
    if (company) {
      this.companyForm.patchValue(company);
      this.idCompany = company.id;
      this.company = company;
      if(company.idManager)
      {
        this.getManagerById(company.idManager)
      }
    }
    this.isShowingCompany = true;
  }

  closeModal(): void {
    this.isShowingCompany = false;
    this.isModalOpen = false;
  }

  openModal(action: 'add' | 'edit', manager?: User, companyId?: number): void {
    this.isEdit = action === 'edit';
    this.modalTitle = this.isEdit ? 'Edit Manager' : 'Add Manager';
    this.modalButtonLabel = this.isEdit ? 'Update Manager' : 'Add Manager';
    if (manager) {
      this.userForm.patchValue(manager);
    } else {
      this.userForm.reset();
    }
    if (companyId) {
      this.idCompany = companyId; // Ensure companyId is passed and used
    }
    this.isModalOpen = true;
  }
  saveCompany(): void {

    console.log(JSON.stringify(this.companyForm.value));
    
    const formData = new FormData();
    formData.append('name', this.companyForm.get('name')?.value);
    formData.append('type', this.companyForm.get('type')?.value);
    formData.append('description', this.companyForm.get('description')?.value);
    formData.append('email', this.companyForm.get('email')?.value);
    formData.append('phone', this.companyForm.get('phone')?.value);
  
    // Log each form data entry
    formData.forEach((value, key) => {
      console.log(`${key}: ${value}`);
    });
  
    this.companyService.createCompany(formData).subscribe(() => {
      this.isShowingCompany = false;
      alert('Company details updated successfully');
    }, error => {
      console.error('Error saving company:', error);
      alert('Failed to update company details.');
    });
  }
  
  onSubmit(): void {
    if (this.userForm.valid) {
        this.saveUser();
    } else {
      alert("Please fill out all required fields correctly.");
    }
  }

  saveUser(): void {
    alert('save user');
    const newUser: User = this.userForm.value;
    console.log(this.userForm.value+ " save idcpm"+ this.idCompany)
    newUser.role = 'manager'; // Ensure the role is set to manager
    if (!newUser.name || !newUser.email || !newUser.password) {
      alert("Please fill out all required fields!!!");
      return;
    }
    if (this.idCompany) {
      this.companyService.setManagerToCompany(newUser, this.idCompany).subscribe(data => {
        this.getAllCompany(); // Refresh the list of companies
        this.closeModal();
        alert("User added successfully");
      }, error => {
        console.error('Error adding user:', error);
        if (error.status === 409 || error.error.message === 'Email already exists') {
          this.userForm.get('email')?.setErrors({ emailExists: true });
        }
      });
      console.log(JSON.stringify(newUser));
    }
  }
}
