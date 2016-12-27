import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'app/service/user.service';
import { User } from 'app/models/user';
import {AuthenticationService} from 'app/service/authentication.service'
import { Configuration } from 'app/app.constants';
 

@Component({
   selector: 'register-form',
   providers: [UserService, AuthenticationService,Configuration],
   templateUrl: 'app/register.component.html'
})
 
export class RegisterComponent {
   
    loading = false;
 
   constructor(
        private _service: AuthenticationService,private _userService: UserService ) {}
 
    register() {
        this.loading = true;
        this. _userService.create(this.model)
            .subscribe(
                data => {
                    // set success message and pass true paramater to persist the message after redirecting to the login page
                   // this.alertService.success('Registration successful', true);
                    this._service.home();
                },
                error => {
                 //   this.alertService.error(error);
                    this.loading = false;
                });
    }
  
}