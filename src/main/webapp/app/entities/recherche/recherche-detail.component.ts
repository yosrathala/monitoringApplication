import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRecherche } from 'app/shared/model/recherche.model';

@Component({
    selector: 'jhi-recherche-detail',
    templateUrl: './recherche-detail.component.html'
})
export class RechercheDetailComponent implements OnInit {
    recherche: IRecherche;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ recherche }) => {
            this.recherche = recherche;
        });
    }

    previousState() {
        window.history.back();
    }
}
