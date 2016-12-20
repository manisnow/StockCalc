import { Component } from '@angular/core';
import { CORE_DIRECTIVES } from '@angular/common';
import { DataService } from 'app/service/dataService';
import { AuthenticationService } from 'app/service/authentication.service';
import { Stock } from 'app/models/stock';
import { Configuration } from 'app/app.constants';



@Component({
    selector: 'stock-form',
    providers: [DataService, AuthenticationService,Configuration],
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
  <label>Investment</label> &nbsp; <input #investTextBox type="text" [(ngModel)] = "investTextValue"><br><br>
   
        <input #textbox type="text" [(ngModel)] ="textValue" (keyup)="onKey($event,textbox.value)">       
        <button (click)="getStock(textbox.value)">get Stocks</button>       
        <button (click)="clearTextArea()">Clear</button> 
        
        <table>
        <thead>
            <th>Stock Name</th>
            <th>Stock Price</th>
            <th>Percentage</th>
            <th>quantity</th>
            <th>Total Amt </th>
            <th> Action</th>
        </thead>
        <tbody>
            <tr *ngFor="let sto of stocks">
                <td>{{sto.t}}</td>
                <td>{{sto.l}}</td> 
                <td><input type="text" [(ngModel)]="sto.percentage" (blur)="updateTotal(sto)" >
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
    private investTextValue="";
    private stocksArray: Array<string> = new Array<string>();
    private stocks: Stock[] = [];
    private stock: Stock;
    private errorMessage:string;
    private isError:boolean=false;
    


 
  
 
    logout() {
        this._service.logout();
    }


    constructor(private _dataService: DataService, private _service:AuthenticationService) {

    }

      ngOnInit(){
        this._service.checkCredentials();
    }

   private closeAlert(){
    this.isError=false;
   }

    private updateStock(value: string) {
        this.stocksArray.push(value);
        this.textAreaValue = this.stocksArray.join("\n");
    }
  
  

    private clearTextArea() {
        this.stocksArray = [];
        this.textAreaValue = this.stocksArray;
    }


    private updateTotal(sto:Stock){
        console.log("Percentage" + sto.percentage);
        console.log("Percentage" + (sto.percentage/100));
        console.log("Percentage" +(this.investTextValue *(sto.percentage/100)));
        console.log("Percentage" + ((this.investTextValue *(sto.percentage/100)) / sto.l));
        if(this.investTextValue == ""){
            this.errorMessage="Please Enter the Investment Amount";
            this.isError=true;
        }else{
          
          sto.qty= Math.floor((this.investTextValue *(sto.percentage/100)) / sto.l_fix);
          sto.totalAmt=sto.qty * sto.l_fix;
        }
    }


    private onKey(event: KeyboardEvent, value: string) { // with type info

        if (event.keyCode == 13) {
            console.log(value + ' char code' + event.keyCode);
            this.getStock(value);
        }

    }

    private getStock(value: string): void {


     var    tempStock:Stock[]=[];
        this._dataService
            .GetSingle(value)
            .subscribe((data: Stock[]) => tempStock = data,
            error => console.log(error),
            () =>               
               {

                this.stocks.push(tempStock[0]);
               console.log('Get all Items complete' + tempStock[0].t);
               
            });
    }

 private removeStock(sto: Stock): void {
  
   
   var index = this.stocks.indexOf(sto, 0);
   if (index > -1) {
   this.stocks.splice(index, 1);
   }

    
    }

  


}


