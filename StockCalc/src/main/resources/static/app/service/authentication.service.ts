import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import { User } from '../models/user';
import { UserService } from './user.service';

 
/*var users = [
  new User('swarna.veena@gmail.com','swarna'),
  new User('manisnow@gmail.com','devarajan')
];*/
 
@Injectable()
export class AuthenticationService {
 
  constructor(private _router: Router,private _userService:UserService){}
 
  logout() {
    localStorage.removeItem("user");
    this._router.navigate(['login']);
  }
  
 register() {
    localStorage.removeItem("user");
    this._router.navigate(['register']);
  }
  
  home() {  
    this._router.navigate(['home']);
  }
 
  login(user){
 
  var authenticatedUser=  this._userService.getById(user.emailid);
   // var authenticatedUser = users.find(u => u.email === user.email);
    if (authenticatedUser && authenticatedUser.password === user.password){
      localStorage.setItem("user", authenticatedUser);
      this._router.navigate(['home']);      
      return true;
    }
    return false;
 
  }
 
   checkCredentials(){
    if (localStorage.getItem("user") === null){
        this._router.navigate(['login']);
    }
  } 
}