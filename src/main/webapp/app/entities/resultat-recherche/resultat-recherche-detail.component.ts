import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResultatRecherche } from 'app/shared/model/resultat-recherche.model';

@Component({
    selector: 'jhi-resultat-recherche-detail',
    templateUrl: './resultat-recherche-detail.component.html'
})
export class ResultatRechercheDetailComponent implements OnInit {
    resultatRecherche: IResultatRecherche;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ resultatRecherche }) => {
            this.resultatRecherche = resultatRecherche;
        });
    }

    previousState() {
        window.history.back();
    }
}
