import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResultatItem } from 'app/shared/model/resultat-item.model';

@Component({
    selector: 'jhi-resultat-item-detail',
    templateUrl: './resultat-item-detail.component.html'
})
export class ResultatItemDetailComponent implements OnInit {
    resultatItem: IResultatItem;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ resultatItem }) => {
            this.resultatItem = resultatItem;
        });
    }

    previousState() {
        window.history.back();
    }
}
