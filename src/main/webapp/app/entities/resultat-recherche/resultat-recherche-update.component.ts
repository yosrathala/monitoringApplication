import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IResultatRecherche } from 'app/shared/model/resultat-recherche.model';
import { ResultatRechercheService } from './resultat-recherche.service';
import { IRecherche } from 'app/shared/model/recherche.model';
import { RechercheService } from 'app/entities/recherche';

@Component({
    selector: 'jhi-resultat-recherche-update',
    templateUrl: './resultat-recherche-update.component.html'
})
export class ResultatRechercheUpdateComponent implements OnInit {
    resultatRecherche: IResultatRecherche;
    isSaving: boolean;

    recherches: IRecherche[];
    date: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected resultatRechercheService: ResultatRechercheService,
        protected rechercheService: RechercheService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ resultatRecherche }) => {
            this.resultatRecherche = resultatRecherche;
            this.date = this.resultatRecherche.date != null ? this.resultatRecherche.date.format(DATE_TIME_FORMAT) : null;
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
        this.resultatRecherche.date = this.date != null ? moment(this.date, DATE_TIME_FORMAT) : null;
        if (this.resultatRecherche.id !== undefined) {
            this.subscribeToSaveResponse(this.resultatRechercheService.update(this.resultatRecherche));
        } else {
            this.subscribeToSaveResponse(this.resultatRechercheService.create(this.resultatRecherche));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IResultatRecherche>>) {
        result.subscribe((res: HttpResponse<IResultatRecherche>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
