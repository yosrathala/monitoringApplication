import { IRecherche } from 'app/shared/model/recherche.model';

export interface IMotcle {
    id?: number;
    nom?: string;
    motinclue?: string;
    motexclue?: string;
    recherches?: IRecherche[];
}

export class Motcle implements IMotcle {
    constructor(
        public id?: number,
        public nom?: string,
        public motinclue?: string,
        public motexclue?: string,
        public recherches?: IRecherche[]
    ) {}
}
