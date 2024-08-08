import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { adminRoutes } from './admin.routes';



@NgModule({
  declarations: [],
  imports: [CommonModule,RouterModule.forChild(adminRoutes)]
})
export class AdminModule { }
