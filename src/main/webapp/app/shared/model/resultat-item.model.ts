import { IResultatRecherche } from 'app/shared/model/resultat-recherche.model';

export interface IResultatItem {
    id?: number;
    contenu?: string;
    statut?: string;
    taux?: string;
    resultatRecherche?: IResultatRecherche;
}

export class ResultatItem implements IResultatItem {
    constructor(
        public id?: number,
        public contenu?: string,
        public statut?: string,
        public taux?: string,
        public resultatRecherche?: IResultatRecherche
    ) {}
}
