import { Moment } from 'moment';
import { IResultatItem } from 'app/shared/model/resultat-item.model';
import { IRecherche } from 'app/shared/model/recherche.model';

export interface IResultatRecherche {
    id?: number;
    date?: Moment;
    resultatItems?: IResultatItem[];
    recherche?: IRecherche;
}

export class ResultatRecherche implements IResultatRecherche {
    constructor(public id?: number, public date?: Moment, public resultatItems?: IResultatItem[], public recherche?: IRecherche) {}
}
