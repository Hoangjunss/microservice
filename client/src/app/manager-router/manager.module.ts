import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { managerRoutes } from './manager.routes';



@NgModule({
  declarations: [],
  imports: [
    CommonModule,RouterModule.forChild(managerRoutes)
  ]
})
export class ManagerModule { }
