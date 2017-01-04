import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/Rx';
import { Observable } from 'rxjs/Observable';
import { Stock } from '../models/stock';
import { UserStock } from '../models/userStock';
import { Configuration } from '../app.constants';

@Injectable()
export class UserStockService {

    private actionUrl: string;
    private headers: Headers;

    constructor(private _http: Http, private _configuration: Configuration) {

        this.actionUrl = _configuration.ServerWithApiUrl + 'stocks/';
        this.headers = new Headers();
        this.headers.append('Content-Type', 'application/json');
        this.headers.append('Accept', 'application/json');
    }
 
 

    public GetSingle = (id: string): Observable<UserStock> => {
        return this._http.get(this.actionUrl + id)
            .map((response: Response) => <UserStock>response.json())
            .catch(this.handleError);
    }

 

    public saveUserStock = (userStock:UserStock): Observable<UserStock> => {
        return this._http.post(this.actionUrl,userStock).
            map((response: Response) => response.json())
            .catch(this.handleError);
    }
    
    private handleError(error: Response) {
        console.error(error);
        return Observable.throw(error.json().error || 'Server error');
    }
}