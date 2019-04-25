import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConf } from 'app/shared/model/conf.model';

@Component({
    selector: 'jhi-conf-detail',
    templateUrl: './conf-detail.component.html'
})
export class ConfDetailComponent implements OnInit {
    conf: IConf;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ conf }) => {
            this.conf = conf;
        });
    }

    previousState() {
        window.history.back();
    }
}
