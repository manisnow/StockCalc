import { Component } from '@angular/core';

@Component({
    selector: 'text-area',
    template: `
        <h1>Stocks</h1>
        <input #textbox type="text" [(ngModel)] ="textValue" >
        <textarea ref-textarea [(ngModel)]="textValue" rows="4"  readonly ></textarea><br/>
        <button (click)="logText(textarea.value)">Update Log</button>
        <button (click)="textValue=''">Clear</button>
        
        <h2>Log <button (click)="log=''">Clear</button></h2>
        <pre>{{log}}</pre>`
})
export class TextAreaComponent {

    private textValue = "initial value";
    private log: string ='';

    private logText(value: string): void {
        this.log += `Text changed to '${value}'\n`
    }
}