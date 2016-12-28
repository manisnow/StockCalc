import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/Rx';
import { Observable } from 'rxjs/Observable';
import { User } from '../models/user';
import { Configuration } from '../app.constants';

 
@Injectable()
export class UserService {
private actionUrl: string;
    private headers: Headers;

    constructor(private _http:Http,private _configuration:Configuration ) {

        this.actionUrl = _configuration.ServerWithApiUrl + 'users/';
        this.headers = new Headers();
        this.headers.append('Content-Type', 'application/json');
        this.headers.append('Accept', 'application/json');
    }
 
 

 
    getAll() {
        return this._http.get(this.actionUrl).map((response: Response) =><User[]> response.json());
    }
 
    getById(id: string) {
        return this._http.get(this.actionUrl + id).map((response: Response) => <User>response.json());
    }
 
    create(user: User) {
        return this._http.get(this.actionUrl , user).map((response: Response) => response.json());
    }
 
    update(user: User) {
        return this._http.get(this.actionUrl  + user.eamilid, user).map((response: Response) => response.json());
    }
 
    delete(id: string) {
        return this._http.get(this.actionUrl + id).map((response: Response) => response.json());
    }
 
    // private helper methods
    }

