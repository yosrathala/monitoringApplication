import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IConf } from 'app/shared/model/conf.model';
import { ConfService } from './conf.service';

@Component({
    selector: 'jhi-conf-update',
    templateUrl: './conf-update.component.html'
})
export class ConfUpdateComponent implements OnInit {
    conf: IConf;
    isSaving: boolean;

    constructor(protected confService: ConfService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ conf }) => {
            this.conf = conf;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.conf.id !== undefined) {
            this.subscribeToSaveResponse(this.confService.update(this.conf));
        } else {
            this.subscribeToSaveResponse(this.confService.create(this.conf));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IConf>>) {
        result.subscribe((res: HttpResponse<IConf>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
