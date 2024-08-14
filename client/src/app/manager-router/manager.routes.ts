import { Routes } from "@angular/router";
import { LoginComponent } from "../components/login/login.component";
import { ManagerLayoutComponent } from "../manager/manager-layout/manager-layout.component";
import { JobLayoutComponent } from "../manager/job-layout/job-layout.component";
import { ApplyLayoutComponent } from "../manager/apply-layout/apply-layout.component";
import { HrLayoutComponent } from "../manager/hr-layout/hr-layout.component";
import { CompanyComponent } from "../manager/company/company.component";
import { ProfileUserComponent } from "../components/profile-user/profile-user.component";


export const managerRoutes: Routes = [
    {
        path: '',
        component: ManagerLayoutComponent,
        children:[
            {
                path: 'job',
                component: JobLayoutComponent
            },
            {
                path: 'apply',
                component: ApplyLayoutComponent
            },
            {
                path: 'hr',
                component: HrLayoutComponent
            }, 
            {
                path: 'about',
                component: CompanyComponent
            },
            {
                path: 'profile/profile-user/:id',
                component: ProfileUserComponent
            }
        ]
    }


];