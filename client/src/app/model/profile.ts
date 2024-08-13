import { Contact } from "./contact";

export class Profile {
    id?:number;
    objective?:string;
    education?:string;
    workExperience?:string;
    skills?:string;
    typeProfile?:string;
    idUser?:number;
    url?:string;
    title?:string;
    contact?:Contact;
    img?:File;
}