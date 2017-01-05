import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import { User } from '../models/user';
import { UserService } from './user.service';
import { StockComponent } from '../stock.component';

 
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
        
      console.log(user.emailid);
       
      localStorage.setItem("user", JSON.stringify(data));
      console.log("localstorage" +  JSON.parse(localStorage.getItem("user")));
       alert(JSON.parse(localStorage.getItem("user"))+ "---"+JSON.stringify(data));
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
 
   checkCredentials(stockC:StockComponent){
     alert("checking credntial"+ localStorage.getItem("user")+" ----"+JSON.parse(localStorage.getItem("user")));
    if (localStorage.getItem("user") === null){
        this._router.navigate(['login']);
    }else{
    var user=JSON.parse(localStorage.getItem("user"));
    alert(user+  localStorage.getItem("user"));
    console.log(user);
    //this.userName=user.emailid;    
    stockC.getStockCalc(user.emailid);
    }
  } 
}