import {Component} from '@angular/core';
import {AuthenticationService} from './service/authentication.service';
import { User}  from './models/user';

 
@Component({
    selector: 'login-form',
    providers: [AuthenticationService],
    templateUrl: 'app/html/signin.html'
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