import { Routes } from "@angular/router";
import { AdminLayoutComponent } from "../layouts/admin-layout/admin-layout.component";
import { UserListComponent } from "../layouts/user-list/user-list.component";
import { ProjectListComponent } from "../layouts/project-list/project-list.component";
import { CompanyListComponent } from "../layouts/company-list/company-list.component";
import { LoginComponent } from "../components/login/login.component";



export const adminRoutes: Routes = [
    {
        path: '',
        component: AdminLayoutComponent,
        children: [
            {
                path: 'list-user',
                component: UserListComponent
            },
            {
                path: 'list-project',
                component: ProjectListComponent
            },
            {
                path: 'list-company',
                component: CompanyListComponent
            }
        ]
    },
    {
        path: 'login',
        component: LoginComponent
    }

];