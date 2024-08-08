import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { CompanyServiceService } from '../../service/company-service.service';
import { Company } from '../../model/company';

@Component({
  selector: 'app-company-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './company-list.component.html',
  styleUrl: './company-list.component.css'
})
export class CompanyListComponent implements OnInit{
  listCompany: Company[]=[];
  companies: any[] = [
    {
      id: 'AO1',
      name: 'Tech Solutions',
      street: '123 Tech Road, Silicon Valley',
      contact: {
        email: 'contact@techsolutions.com',
        phone: '+1-234-567-8901',
        website: 'https://www.techsolutions.com'
      }
    },
    {
      id: 'AO2',
      name: 'Innovate Inc.',
      street: '456 Innovation Blvd, New York',
      contact: {
        email: 'info@innovateinc.com',
        phone: '+1-987-654-3210',
        website: 'https://www.innovateinc.com'
      }
    },
    {
      id: 'AO3',
      name: 'Global Enterprises',
      street: '789 Global St, London',
      contact: {
        email: 'support@globalenterprises.com',
        phone: '+44-123-456-7890',
        website: 'https://www.globalenterprises.com'
      }
    }
  ];

  constructor(private companyService: CompanyServiceService) {}
    
  ngOnInit(): void {
    console.log("Company start");
    this.getAllCompany();
  }

  getAllCompany(){
    this.companyService.getAllCompanies().subscribe(data=>{
      console.log(data);
      this.listCompany=data;
    });
  }

  
}
