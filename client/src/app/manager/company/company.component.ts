import { routes } from './../../app.routes';
import { Component, OnInit } from '@angular/core';
import { CompanyServiceService } from '../../service/company-service.service';
import { Company } from '../../model/company';
import { UserServiceService } from '../../service/user-service.service';
import { User } from '../../model/user';

@Component({
  selector: 'app-company',
  standalone: true,
  imports: [],
  templateUrl: './company.component.html',
  styleUrl: './company.component.css'
})
export class CompanyComponent implements OnInit {
  company:Company = new Company();
  userCurrent: User = new User();


  constructor(private companyService:CompanyServiceService) { }

  ngOnInit(): void {
    alert("About");
    const userCurrentString = localStorage.getItem('userCurrent');
    if (userCurrentString) {
      this.userCurrent = JSON.parse(userCurrentString);
    }
    if(this.userCurrent.role === 'manager' && this.userCurrent.id){
      this.getCompanyByManager(this.userCurrent.id);
    }else{

    }
  }

  getCompanyByManager(idManager:number){
    this.companyService.getCompanyByManager(idManager).subscribe(data => {
      this.company = data;
      console.log(this.company);
      localStorage.setItem('idCompany', this.company.id+'');
      this.companyService.changeCompany(this.company);
    });
  }
  
  
  // getCompanies(id:number) {
  //   this.companyService.getCompanyById(id).subscribe(data=>{
  //     this.company = data;
  //   }
  //   );
  
  // }

}
