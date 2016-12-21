import { Injectable } from '@angular/core';
 
@Injectable()
export class Configuration {
    public Server: string = "/";
    public ApiUrl: string = "";
    public ServerWithApiUrl = this.Server + this.ApiUrl;
}