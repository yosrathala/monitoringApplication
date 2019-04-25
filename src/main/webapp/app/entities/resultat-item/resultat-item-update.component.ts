import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IResultatItem } from 'app/shared/model/resultat-item.model';
import { ResultatItemService } from './resultat-item.service';
import { IResultatRecherche } from 'app/shared/model/resultat-recherche.model';
import { ResultatRechercheService } from 'app/entities/resultat-recherche';

@Component({
    selector: 'jhi-resultat-item-update',
    templateUrl: './resultat-item-update.component.html'
})
export class ResultatItemUpdateComponent implements OnInit {
    resultatItem: IResultatItem;
    isSaving: boolean;

    resultatrecherches: IResultatRecherche[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected resultatItemService: ResultatItemService,
        protected resultatRechercheService: ResultatRechercheService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ resultatItem }) => {
            this.resultatItem = resultatItem;
        });
        this.resultatRechercheService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IResultatRecherche[]>) => mayBeOk.ok),
                map((response: HttpResponse<IResultatRecherche[]>) => response.body)
            )
            .subscribe(
                (res: IResultatRecherche[]) => (this.resultatrecherches = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.resultatItem.id !== undefined) {
            this.subscribeToSaveResponse(this.resultatItemService.update(this.resultatItem));
        } else {
            this.subscribeToSaveResponse(this.resultatItemService.create(this.resultatItem));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IResultatItem>>) {
        result.subscribe((res: HttpResponse<IResultatItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackResultatRechercheById(index: number, item: IResultatRecherche) {
        return item.id;
    }
}
