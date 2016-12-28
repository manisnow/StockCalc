import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'app/service/user.service';
import { User } from 'app/models/user';
import { NewUser } from 'app/models/newuser';

import {AuthenticationService} from 'app/service/authentication.service'
import { Configuration } from 'app/app.constants';
 

@Component({
   selector: 'register-form',
   providers: [UserService, AuthenticationService,Configuration],
   templateUrl: 'app/register.component.html'
})
 
export class RegisterComponent {
    errorMsg:string;
    user:User =new User;
    newuser:NewUser =new NewUser();
    loading = false;
 
   constructor(
        private _service: AuthenticationService,private _userService: UserService ) {}
 
    register() {
        this.loading = true;
        this.user=this.newuser;
        this. _userService.create(this.user)
            .subscribe(
                data => {
                    // set success message and pass true paramater to persist the message after redirecting to the login page
                   // this.alertService.success('Registration successful', true);
                    this._service.home();
                },
                error => {
                 //   this.alertService.error(error);
                 this.errorMsg=error;
                    this.loading = false;
                });
    }

    signInPage(){
    this._service.home();
    }
  
}