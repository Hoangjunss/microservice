import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { ProfileComponent } from './components/profile/profile.component';
import { CreateProfileComponent } from './components/create-profile/create-profile.component';
import { ProfileListComponent } from './components/profile-list/profile-list.component';
import { ProjectListComponent } from './components/project-list/project-list.component';
import { ProfileUserComponent } from './components/profile-user/profile-user.component';

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
        component: ProjectListComponent,
    }
    

];
