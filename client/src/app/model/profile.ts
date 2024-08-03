export class Profile {
    id?:number;
    objective?:string;
    education?:string;
    workExperience?:string;
    skills?:string;
    typeProfile?:string;
    userId?:number;
    url?:string;
    title?:string;
    contact?:Contact;
}
class Contact{
    address?: string;
  phone?: string;
  email?: string;
}