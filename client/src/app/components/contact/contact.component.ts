import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ContactServiceService } from '../../service/contact-service.service';
import { Contact } from '../../model/contact';

@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.css'
})
export class ContactComponent implements OnInit {
  @Input() contact?: Contact;
  constructor(private contactService:ContactServiceService){};
  ngOnInit(): void {
    
  }
  getContact(id:number){
    this.contactService.getContactByUser(id).subscribe(data=>{
        this.contact=data;
    })
  }
  createContact(contact:Contact){
    this.contactService.createContactByUser(contact).subscribe(data=>{
      this.contact=data;
    })
  }
  updateContact(contact:Contact){
    this.contactService.updateContactByUser(contact).subscribe(data=>{
      this.contact=data;
    })
  }
}
