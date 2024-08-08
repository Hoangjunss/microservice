import { ManagerModule } from './manager-router/manager.module';
import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { ProfileComponent } from './components/profile/profile.component';
import { CreateProfileComponent } from './components/create-profile/create-profile.component';
import { ProfileListComponent } from './components/profile-list/profile-list.component';
import { ProjectListComponent } from './components/project-list/project-list.component';
import { ProfileUserComponent } from './components/profile-user/profile-user.component';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { UserComponent } from './components/user/user.component';



export const routes: Routes = [
    {
        path: '',
        redirectTo: '/home',
        pathMatch: 'full'
    },
    {
        path: 'home',
        component: HomeComponent
    },
    {
        path: 'profile',
        component: ProfileComponent,
        children:[
            {
                path:'create',
                component:CreateProfileComponent
            },
            {
                path:'list-profiles',
                component:ProfileListComponent
            },
            {
                path: 'profile-user',
                component: ProfileUserComponent
            }
        ]
    },
    {
        path: 'list-project',
        component: ProjectListComponent
    },
    {
        path: 'user',
        component: UserComponent
    },
    {
        path: 'register',
        component: RegisterComponent
    },
    {
        path: 'login',
        component: LoginComponent
    },
    {
        path: 'admin',
        loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule)
    },
    {
        path: 'manager',
        loadChildren: () => import('./manager-router/manager.module').then(m => m.ManagerModule)
    }



];
