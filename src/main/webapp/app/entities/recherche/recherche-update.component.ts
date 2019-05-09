import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRecherche } from 'app/shared/model/recherche.model';
import { RechercheService } from './recherche.service';
import { IMotcle } from 'app/shared/model/motcle.model';
import { MotcleService } from 'app/entities/motcle';
import { ISource } from 'app/shared/model/source.model';
import { SourceService } from 'app/entities/source';

@Component({
    selector: 'jhi-recherche-update',
    templateUrl: './recherche-update.component.html'
})
export class RechercheUpdateComponent implements OnInit {
    recherche: IRecherche;
    isSaving: boolean;

    motcles: IMotcle[];

    sources: ISource[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected rechercheService: RechercheService,
        protected motcleService: MotcleService,
        protected sourceService: SourceService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ recherche }) => {
            this.recherche = recherche;
        });
        this.motcleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMotcle[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMotcle[]>) => response.body)
            )
            .subscribe((res: IMotcle[]) => (this.motcles = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.sourceService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISource[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISource[]>) => response.body)
            )
            .subscribe((res: ISource[]) => (this.sources = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.recherche.id !== undefined) {
            this.subscribeToSaveResponse(this.rechercheService.update(this.recherche));
        } else {
            this.subscribeToSaveResponse(this.rechercheService.create(this.recherche));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecherche>>) {
        result.subscribe((res: HttpResponse<IRecherche>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMotcleById(index: number, item: IMotcle) {
        return item.id;
    }

    trackSourceById(index: number, item: ISource) {
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
