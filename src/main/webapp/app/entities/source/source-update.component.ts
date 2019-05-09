import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISource } from 'app/shared/model/source.model';
import { SourceService } from './source.service';
import { IRecherche } from 'app/shared/model/recherche.model';
import { RechercheService } from 'app/entities/recherche';

@Component({
    selector: 'jhi-source-update',
    templateUrl: './source-update.component.html'
})
export class SourceUpdateComponent implements OnInit {
    source: ISource;
    isSaving: boolean;

    recherches: IRecherche[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected sourceService: SourceService,
        protected rechercheService: RechercheService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ source }) => {
            this.source = source;
        });
        this.rechercheService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRecherche[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRecherche[]>) => response.body)
            )
            .subscribe((res: IRecherche[]) => (this.recherches = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.source.id !== undefined) {
            this.subscribeToSaveResponse(this.sourceService.update(this.source));
        } else {
            this.subscribeToSaveResponse(this.sourceService.create(this.source));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISource>>) {
        result.subscribe((res: HttpResponse<ISource>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackRechercheById(index: number, item: IRecherche) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
