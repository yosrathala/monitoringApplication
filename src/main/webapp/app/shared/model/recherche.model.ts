import { ISource } from 'app/shared/model/source.model';
import { IMotcle } from 'app/shared/model/motcle.model';
import { IResultatRecherche } from 'app/shared/model/resultat-recherche.model';

export interface IRecherche {
    id?: number;
    periodicite?: number;
    emailnotif?: boolean;
    pushnotif?: boolean;
    smsnotif?: boolean;
    source?: ISource;
    motcle?: IMotcle;
    resultatRecherches?: IResultatRecherche[];
}

export class Recherche implements IRecherche {
    constructor(
        public id?: number,
        public periodicite?: number,
        public emailnotif?: boolean,
        public pushnotif?: boolean,
        public smsnotif?: boolean,
        public source?: ISource,
        public motcle?: IMotcle,
        public resultatRecherches?: IResultatRecherche[]
    ) {
        this.emailnotif = this.emailnotif || false;
        this.pushnotif = this.pushnotif || false;
        this.smsnotif = this.smsnotif || false;
    }
}
