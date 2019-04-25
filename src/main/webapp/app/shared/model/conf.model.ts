export interface IConf {
    id?: number;
    heuredebut?: number;
    heurefin?: number;
    smtphost?: string;
    smtppassword?: string;
    pushserver?: string;
    smtpuser?: string;
    smsgateway?: string;
}

export class Conf implements IConf {
    constructor(
        public id?: number,
        public heuredebut?: number,
        public heurefin?: number,
        public smtphost?: string,
        public smtppassword?: string,
        public pushserver?: string,
        public smtpuser?: string,
        public smsgateway?: string
    ) {}
}
