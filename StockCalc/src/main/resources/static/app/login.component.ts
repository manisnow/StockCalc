import {Component, ElementRef} from '@angular/core';
import {AuthenticationService} from './service/authentication.service';
import { UserService } from './service/user.service';
import { User}  from './models/user';

 
@Component({
    selector: 'login-form',
    providers: [AuthenticationService,UserService],
    template: `
        <div class="container" >
            <div class="title">
                Welcome
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="input-field col s12">
                        <input [(ngModel)]="user.email" id="email" 
                            type="email" class="validate">
                        <label for="email">Email</label>
                    </div>
                </div>
 
                <div class="row">
                    <div class="input-field col s12">
                        <input [(ngModel)]="user.password" id="password" 
                            type="password" class="validate">
                        <label for="password">Password</label>
                    </div>
                </div>
 
                <span>{{errorMsg}}</span>
                <button (click)="login()" 
                    class="btn waves-effect waves-light" 
                    type="submit" name="action">Login</button>
            </div>
             <a (click)="register()" href="register">new user register here</a>
        </div>
    	`
})
 
export class LoginComponent {
 
    public user = new User();
    public errorMsg = '';
 
    constructor(
        private _service:AuthenticationService) {}
 
    login() {
        if(!this._service.login(this.user)){
            this.errorMsg = 'Failed to login';
        }
    }
  
   register() {
    this._service.register();
  }
  
}