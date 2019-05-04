import { IUser } from 'app/core/user/user.model';

export interface IUserExtra {
    id?: number;
    phone?: string;
    phushid?: string;
    user?: IUser;
}

export class UserExtra implements IUserExtra {
    constructor(public id?: number, public phone?: string, public phushid?: string, public user?: IUser) {}
}
