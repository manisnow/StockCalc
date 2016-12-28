import { Component } from '@angular/core';
import { CORE_DIRECTIVES } from '@angular/common';
import { DataService } from './service/dataService';
import { AuthenticationService } from './service/authentication.service';
import { Stock } from './models/stock';
import { Configuration } from './app.constants';




@Component({
  selector: 'stock-form',
  providers: [DataService, AuthenticationService, Configuration],
  template: `


  <div class="container" >
                <div class="content">
                    <span>Congratulations, you have successfully logged in!!</span>
                    <br />
                    <a (click)="logout()" href="#">Click Here to logout</a>
              

  <h1>Stocks</h1><br>

    <div *ngIf="isError"  >       
  <label > {{errorMessage}} </label> 
          <button (click)="closeAlert()">Hide</button>   
          
        </div><br>
      
      <select  [(ngModel)]="exchangeName">
        <option *ngFor="let value of exchangeNames" [ngValue]="value">{{value}}</option>
      </select>
      
  
        <label>Investment</label> &nbsp; <input #investTextBox type="number"  [(ngModel)] = "investTextValue"    min=1 max=9999999999 step=5><br><br>
        <input #textbox type="text" [(ngModel)] ="textValue" (keyup)="onKey($event,textbox.value)">       
        <button (click)="getStock(textbox.value)">get Stocks</button>       
        <button (click)="clearTextArea()">Clear</button> 
        
        <table>
        <thead>
            <th>Excahnge</th>
            <th>Stock Name</th>
            <th>Stock Price</th>
            <th>Percentage</th>
            <th>quantity</th>
            <th>Total Amt </th>
            <th> Action</th>
        </thead>
        <tbody>
            <tr *ngFor="let sto of stocks">
                <td>{{sto.e}}</td>
                <td>{{sto.t}}</td>
                <td>{{sto.l}}</td> 
                <td><input type="number"  [(ngModel)]="sto.percentage"  min=1 max=100 step=5 (keyup)="updateTotal(sto)" >
         
                     </td>



                <td>{{sto.qty}} </td>
                 <td>{{sto.totalAmt}} </td>
                <td> <button (click)="removeStock(sto)">Remove</button> </td>
            </tr>
        </tbody>
    </table>
        <table id="displayTable" > </table>
        </div>
            </div>
        `,
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
  private textAreaValue = "";
  private investTextValue = "";
  private stocksArray: Array<string> = new Array<string>();
  private stocks: Stock[] = [];
  private stock: Stock;
  private errorMessage: string;
  private isError: boolean = false;
  private exchangeNames: string[] =['NSE','BOM','NYSE'];
  private exchangeName: string=this.exchangeNames[0];




  logout() {
    this._service.logout();
  }


  constructor(private _dataService: DataService, private _service: AuthenticationService) {

  }

  ngOnInit() {
    this._service.checkCredentials();
  }

  
 
   private selectExchange(value:string) {
    this.exchangeName = value;
  }
  
  private closeAlert() {
    this.isError = false;
  }

  private updateStock(value: string) {
    this.stocksArray.push(value);
    this.textAreaValue = this.stocksArray.join("\n");
  }



  private clearTextArea() {
    this.stocksArray = [];
    this.textAreaValue = this.stocksArray;
  }


  private updateTotal(sto: Stock) {

    if (this.getTotalStockPercentage() > 100) {

      sto.percentage = 0;
      this.errorMessage = "Total Percentage can not bemore than 100 %";
      this.isError = true;
    } else {
      
    
    console.log("Percentage" + sto.percentage);
    console.log("Percentage" + (sto.percentage / 100));
    console.log("Percentage" + (this.investTextValue * (sto.percentage / 100)));
    console.log("Percentage" + ((this.investTextValue * (sto.percentage / 100)) / sto.l));
    if (this.investTextValue == "") {
      this.errorMessage = "Please Enter the Investment Amount";
      this.isError = true;
    } else {

      sto.qty = Math.floor((this.investTextValue * (sto.percentage / 100)) / sto.l_fix);
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

    for (let sto of this.stocks) {
      percentage += sto.percentage;
    }



    return percentage;
  }

  private getStock(value: string): void {

      console.log("exchange name"+ this.exchangeName);
    if(this.exchangeName==""){
      this.errorMessage="Please Select the Exchange name";
      this.isError=true;
    }else{
    
    var tempStock: Stock[] = [];
    this._dataService
      .GetSingleByExcahngeName(value,this.exchangeName)
      .subscribe((data: Stock[]) => tempStock = data,
      error => console.log(error),
      () => {

        this.stocks.push(tempStock[0]);
        console.log('Get all Items complete' + tempStock[0].t);

      });
  }
  }

  private removeStock(sto: Stock): void {


    var index = this.stocks.indexOf(sto, 0);
    if (index > -1) {
      this.stocks.splice(index, 1);
    }


  }




}


