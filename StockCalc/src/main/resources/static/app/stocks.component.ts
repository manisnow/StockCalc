import {Component} from '@angular/core';

@Component({
   selector: 'stocks' ,
   template:'app/stocks.html'
})
export class StocksComponent {ngOnInit() {
    console.log('card component loaded!');
  } }