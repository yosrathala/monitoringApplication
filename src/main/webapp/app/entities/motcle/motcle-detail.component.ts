import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMotcle } from 'app/shared/model/motcle.model';

@Component({
    selector: 'jhi-motcle-detail',
    templateUrl: './motcle-detail.component.html'
})
export class MotcleDetailComponent implements OnInit {
    motcle: IMotcle;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ motcle }) => {
            this.motcle = motcle;
        });
    }

    previousState() {
        window.history.back();
    }
}
