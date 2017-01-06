
import { User } from './user';
import { Stock } from './stock';

export class UserStock extends User {

    public stocks:Stock[]=[];
    public invAmt:number;
    public investSchdAlertType; 

}