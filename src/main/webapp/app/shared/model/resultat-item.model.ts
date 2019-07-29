import { IResultatRecherche } from 'app/shared/model/resultat-recherche.model';

export interface IResultatItem {
    id?: number;
    contenu?: string;
    idr?: string;
    statu?: boolean;
    note?: boolean;
    titre?: string;
    date?: string;
    url?: string;
    resultatRecherche?: IResultatRecherche;
}

export class ResultatItem implements IResultatItem {
    constructor(
        public id?: number,
        public contenu?: string,
        public idr?: string,
        public statu?: boolean,
        public note?: boolean,
        public titre?: string,
        public date?: string,
        public url?: string,
        public resultatRecherche?: IResultatRecherche
    ) {
        this.statu = this.statu || false;
        this.note = this.note || false;
    }
}
