import { IRecherche } from 'app/shared/model/recherche.model';

export interface ISource {
    id?: number;
    nom?: string;
    login?: string;
    motPasse?: string;
    url?: string;
    key?: string;
    dataHandler?: string;
    recherches?: IRecherche[];
}

export class Source implements ISource {
    constructor(
        public id?: number,
        public nom?: string,
        public login?: string,
        public motPasse?: string,
        public url?: string,
        public key?: string,
        public dataHandler?: string,
        public recherches?: IRecherche[]
    ) {}
}
