import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/Rx';
import { Observable } from 'rxjs/Observable';
import { Stock } from '../models/stock';
import { UserStock } from '../models/userStock';
import { Configuration } from '../app.constants';

@Injectable()
export class DataService {

    private actionUrl: string;
    private headers: Headers;

    constructor(private _http: Http, private _configuration: Configuration) {

        this.actionUrl = _configuration.ServerWithApiUrl + 'stock/';
        this.headers = new Headers();
        this.headers.append('Content-Type', 'application/json');
        this.headers.append('Accept', 'application/json');
    }
 
  /*  public GetAll = (): Observable<Stock[]> => {
        return this._http.get(this.actionUrl)
            .map((response: Response) => <Stock[]>response.json())
            .catch(this.handleError);
    }*/

    public GetSingle = (id: string): Observable<Stock[]> => {
        return this._http.get(this.actionUrl + id)
            .map((response: Response) => <Stock[]>response.json())
            .catch(this.handleError);
    }

  public GetSingleByExcahngeName = (id: string,exchangeName:string): Observable<Stock[]> => {
        return this._http.get(this.actionUrl + id+"?exchange="+exchangeName)
            .map((response: Response) => <Stock[]>response.json())
            .catch(this.handleError);
    }

    public saveUserStock = (userStock:UserStock): Observable<UserStock> => {
        return this._http.post(this.actionUrl,userStock).
            map((response: Response) => response.json())
            .catch(this.handleError);
    }
    /*public Add = (itemName: string): Observable<Stock> => {
        let toAdd = JSON.stringify({ ItemName: itemName });

        return this._http.post(this.actionUrl, toAdd, { headers: this.headers })
            .map((response: Response) => <Stock>response.json())
            .catch(this.handleError);
    }*/

    /*public Update = (id: number, itemToUpdate: Stock): Observable<Stock> => {
        return this._http.put(this.actionUrl + id, JSON.stringify(itemToUpdate), { headers: this.headers })
            .map((response: Response) => <Stock>response.json())
            .catch(this.handleError);
    }*/

    /*public Delete = (id: number): Observable<Response> => {
        return this._http.delete(this.actionUrl + id)
            .catch(this.handleError);
    }*/

    private handleError(error: Response) {
        console.error(error);
        return Observable.throw(error.json().error || 'Server error');
    }
}