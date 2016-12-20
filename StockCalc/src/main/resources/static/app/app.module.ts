import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpModule }      from '@angular/http';
import { FormsModule }   from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent }  from './app.component';
import { LoginComponent } from './login.component';
import { StockComponent } from  './stock.component'; 


const appRoutes: Routes = [
{ path: '' ,  redirectTo :'home',pathMatch: 'full'  },
{ path: 'home' ,  component: StockComponent },
{ path: 'login',  component: LoginComponent }

];



@NgModule({
  imports: [
    BrowserModule,
    HttpModule,
    FormsModule,
    RouterModule.forRoot(appRoutes)
  ],
  declarations: [
    AppComponent,LoginComponent,StockComponent],
  bootstrap: [ AppComponent]
})
export class AppModule { }


/*
Copyright 2016 Google Inc. All Rights Reserved.
Use of this source code is governed by an MIT-style license that
can be found in the LICENSE file at http://angular.io/license
*/