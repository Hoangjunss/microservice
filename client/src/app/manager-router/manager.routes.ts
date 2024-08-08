import { Routes } from "@angular/router";
import { LoginComponent } from "../components/login/login.component";
import { ManagerLayoutComponent } from "../manager/manager-layout/manager-layout.component";
import { JobLayoutComponent } from "../manager/job-layout/job-layout.component";
import { ApplyLayoutComponent } from "../manager/apply-layout/apply-layout.component";
import { HrLayoutComponent } from "../manager/hr-layout/hr-layout.component";


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
                path: 'emloyee',
                component: HrLayoutComponent
            }
        ]
    }


];