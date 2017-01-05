import { Component } from '@angular/core';
import { CORE_DIRECTIVES } from '@angular/common';
import { DataService } from './service/dataService';
import { UserStockService } from './service/userstock.service';
import { UserService } from './service/user.service';
import { AuthenticationService } from './service/authentication.service';
import { Stock } from './models/stock';
import { User } from './models/user';
import { UserStock } from './models/userStock';
import { Configuration } from './app.constants';




@Component({
  selector: 'stock-form',
  providers: [UserStockService,DataService,UserService, AuthenticationService, Configuration],
  templateUrl: 'app/html/stocksCalc.html',
  directives: [CORE_DIRECTIVES]
})
export class StockComponent {

  /*stock.setId("784961");
    stock.setT("TCS");
    stock.setE("NSE");
    stock.setL("2,299.80");
    stock.setL_fix("2,299.80");
    stock.setL_cur("&#8377;2,299.80");
    stock.setS("0");
    stock.setLtt("3:48PM GMT+5:30");*/
  private textValue = "";  
  private investTextValue = "";
  private stocksArray: Array<string> = new Array<string>();
  private stock: Stock;
  private errorMsg: string;
  private isError: boolean = false;
  private exchangeNames: string[] =['NSE','BOM','NYSE'];
  private exchangeName: string=this.exchangeNames[0];
  private userStock:UserStock=new UserStock();
  private actionMessage:string="";
  private userName:string="";


  logout() {
    alert("logout called");
    this._service.logout();
  }


  constructor(private _dataService:DataService,private _userService:UserService,private _userStockService: UserStockService, private _service: AuthenticationService) {

  }

  ngOnInit() {
    this._service.checkCredentials(this);
   
    
  }

  
 
  private selectExchange(value:string) {
    this.exchangeName = value;
  }
  
  private closeAlert() {
    this.isError = false;
  }

  private updateStock(value: string) {
    this.stocksArray.push(value);    
  }

  private  saveStockCalc(){   
  this.userStock.emailid=this.userName;
 console.log("Save  stock called" + this.userStock.emailid);
    this._userStockService.saveUserStock(this.userStock).subscribe(
                data => { 
                  this.actionMessage="Stocks Saved";
                },
                error => {
                 this.isError=true;
                 this.errorMsg=JSON.parse(error);
                });
   
  }

  public  getStockCalc(emailid:string){
    this.userName=emailid;

    this._userStockService.GetSingle(emailid).subscribe(
                data => { 
                  this.userStock=data;
                  this.actionMessage="Stocks Fetched";
                },
                error => {
                 this.isError=true;
                 this.errorMsg=JSON.parse(error);
                });
   
  }



  private clearTextArea() {
    this.stocksArray = [];    
  }


  private updateTotal(sto: Stock) {

    if (this.getTotalStockPercentage() > 100) {

      sto.percentage = 0;
      this.errorMsg = "Total Percentage can not bemore than 100 %";
      this.isError = true;
    } else {
      
    
    console.log("Percentage" + sto.percentage);
    console.log("Percentage" + (sto.percentage / 100));
    console.log("Percentage" + (this.userStock.invAmt * (sto.percentage / 100)));
    console.log("Percentage" + ((this.userStock.invAmt * (sto.percentage / 100)) / sto.l));
    if (this.userStock.invAmt == 0) {
      this.errorMsg = "Please Enter the Investment Amount";
      this.isError = true;
    } else {

      sto.qty = Math.floor((this.userStock.invAmt * (sto.percentage / 100)) / sto.l_fix);
      sto.totalAmt = sto.qty * sto.l_fix;
     }
      
    }
  }


  private onKey(event: KeyboardEvent, value: string) { // with type info

    if (event.keyCode == 13) {
      console.log(value + ' char code' + event.keyCode);
      this.textValue = '';
      this.getStock(value);
    }

  }

  private getTotalStockPercentage(): number {
    var percentage: number = 0;

    for (let sto of this.userStock.stocks) {
      percentage += sto.percentage;
    }



    return percentage;
  }

  private getStock(value: string): void {

      console.log("exchange name"+ this.exchangeName);
    if(this.exchangeName==""){
      this.errorMsg="Please Select the Exchange name";
      this.isError=true;
    }else{
    
    var tempStock: Stock[] = [];
    this._dataService
      .GetSingleByExcahngeName(value,this.exchangeName)
      .subscribe((data: Stock[]) => tempStock = data,
      error => console.log(error),
      () => {

        this.userStock.stocks.push(tempStock[0]);
        console.log('Get all Items complete' + tempStock[0].t);

      });
  }
  }

  private removeStock(sto: Stock): void {


    var index = this.userStock.stocks.indexOf(sto, 0);
    if (index > -1) {
      this.userStock.stocks.splice(index, 1);
    }


  }




}


