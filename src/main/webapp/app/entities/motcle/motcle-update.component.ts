import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IMotcle } from 'app/shared/model/motcle.model';
import { MotcleService } from './motcle.service';

@Component({
    selector: 'jhi-motcle-update',
    templateUrl: './motcle-update.component.html'
})
export class MotcleUpdateComponent implements OnInit {
    motcle: IMotcle;
    isSaving: boolean;

    constructor(protected motcleService: MotcleService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ motcle }) => {
            this.motcle = motcle;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.motcle.id !== undefined) {
            this.subscribeToSaveResponse(this.motcleService.update(this.motcle));
        } else {
            this.subscribeToSaveResponse(this.motcleService.create(this.motcle));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMotcle>>) {
        result.subscribe((res: HttpResponse<IMotcle>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
