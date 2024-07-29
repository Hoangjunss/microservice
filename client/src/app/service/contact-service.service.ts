import { Injectable } from '@angular/core';
import { Contact } from '../model/contact';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ContactServiceService {
  
  constructor(private http:HttpClient) { }
  getContactByUser(id:number):Observable<Contact>{
    return this.http.get<Contact>('http://localhost:8085/contact/getByUser?idUser'+id);
  }
  createContactByUser(contact:Contact):Observable<Contact>{
    return this.http.post<Contact>('http://localhost:8085/contact/save',contact);
  }
  updateContactByUser(contact:Contact):Observable<Contact>{
    return this.http.put<Contact>('http://localhost:8085/contact/update',contact);
  }
}
