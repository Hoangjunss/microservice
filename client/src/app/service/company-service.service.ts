import { Apiresponse } from './../apiresponse';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Company } from '../model/company';
import { BehaviorSubject, map, Observable, observeOn } from 'rxjs';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class CompanyServiceService {

  private companySource = new BehaviorSubject<Company|null>(null);
  company$ = this.companySource.asObservable();

  constructor(private httpClient: HttpClient) { }
  private baseURL="http://localhost:8080/manager/";

  changeCompany(company: Company){
    this.companySource.next(company);
  }

  createCompany(company: Company): Observable<Company> {
    let headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const authHeaders = this.createAuthorizationHeader();
    if (authHeaders.has('Authorization')) {
        headers = headers.set('Authorization', authHeaders.get('Authorization')!);
    }
    return this.httpClient.post<Apiresponse<Company>>(`${this.baseURL}admin/company/create`, company, { headers }).pipe(
      map(response=>{
        if (response.success) {
          return this.mapToCompany(response.data);
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  updateCompany(company: Company): Observable<Company>{
    let headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const authHeaders = this.createAuthorizationHeader();
    if (authHeaders.has('Authorization')) {
        headers = headers.set('Authorization', authHeaders.get('Authorization')!);
    }
    return this.httpClient.post<Apiresponse<Company>>(`${this.baseURL}manager/company/update`, company, { headers }).pipe(
      map(response=>{
        if (response.success) {
          return this.mapToCompany(response.data);
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  deleteCompany(id:number): void{
    const authHeaders = this.createAuthorizationHeader();
    this.httpClient.post<Apiresponse<String>>(`${this.baseURL}admin/company/delete?id=${id}`, { authHeaders }).pipe(
      map(response=>{
        if (!response.success) {
          throw new Error(response.message);
        }
        alert('Delete company completed successfully')
      })
    );
  }

  getCompanyById(id?: number): Observable<Company> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<Company>>(`${this.baseURL}user/company/getbyid?id=${id}`, { headers }).pipe(
      map(response=>{
        if (response.success) {
          return this.mapToCompany(response.data);
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getCompanyByIddHr(id:number): Observable<Company> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<Company>>(`${this.baseURL}hr/findByIdHr?id=${id}`, { headers}).pipe(
      map(response=>{
        if (response.success) {
          return this.mapToCompany(response.data);
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getCompanyByManager(idManager?: number): Observable<Company> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<Company>>(`${this.baseURL}company/getcompanybyidmanager?managerId=${idManager}`, { headers}).pipe(
      map(response=>{
        if (response.success) {
          return this.mapToCompany(response.data);
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  setHrToCompany(user:User, idCompany: number): Observable<Company>{
    let headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const authHeaders = this.createAuthorizationHeader();
    if (authHeaders.has('Authorization')) {
        headers = headers.set('Authorization', authHeaders.get('Authorization')!);
    }
    return this.httpClient.put<Apiresponse<Company>>(`${this.baseURL}manager/sethrtocompany?idCompany=${idCompany}`, user, { headers }).pipe(
      map(response=>{
        if (response.success) {
          return this.mapToCompany(response.data);
        } else {
          throw new Error(response.message);
        }
      })
    );
  }

  getAllCompanies(): Observable<Company[]> {
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<Company[]>>(`${this.baseURL}user/company/getcompany`, { headers}).pipe(
      map((response) => {
        if (response.success) {
          return response.data.map(this.mapToCompany);
        } else {
          throw new Error(response?.message || 'Unknown error');
        }
      })
    );
  }

  getCompanyByType(type:string): Observable<Company[]>{
    const headers = this.createAuthorizationHeader();
    return this.httpClient.get<Apiresponse<Company[]>>(`${this.baseURL}user/company/getcompanybytype?type=${type}`, { headers }).pipe(
      map(response=>{
        if (response.success) {
          return response.data.map(this.mapToCompany);
        } else {
          throw new Error(response.message);
        }
      })
    );
  }


  private mapToCompany(companyDTO: any): Company {
    return {
      id: companyDTO.id,
      name: companyDTO.name,
    type: companyDTO.type,
    description: companyDTO.description,
    street: companyDTO.street,
    email: companyDTO.email,
    phone: companyDTO.phone,
    city: companyDTO.city,
    country: companyDTO.country,
    idManager: companyDTO.idManager,
    idHR: companyDTO.idHR,
    idJobs: companyDTO.idJobs,
    image: companyDTO.image
    };
  }

  private createAuthorizationHeader(): HttpHeaders {
    const token = localStorage.getItem('authToken');
    if(token){
      console.log('Token found in local store:', token);
      return new HttpHeaders().set('Authorization', `Bearer ${token}`);
    }
    else
    {
      console.log('Token not found in local store');
    }
    return new HttpHeaders();
  }

}
