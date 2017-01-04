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
       console.log("login User" + user);
 console.log("login User" + user.emailid);
 
   this._userService.getById(user.emailid).subscribe(
    data => {
        
      if (data && data.password === user.password){
      localStorage.setItem("user", user.emailid);
      this._router.navigate(['home']);      
      return true;
    }else{
      console.log("password did not match");
      return false;
    }

    },
    error => {
      console.log(error);
    }


  );
      
 
  }
 
   checkCredentials(){
    if (localStorage.getItem("user") === null){
        this._router.navigate(['login']);
    }
  } 
}