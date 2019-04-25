import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRecherche } from 'app/shared/model/recherche.model';
import { RechercheService } from './recherche.service';
import { ISource } from 'app/shared/model/source.model';
import { SourceService } from 'app/entities/source';
import { IMotcle } from 'app/shared/model/motcle.model';
import { MotcleService } from 'app/entities/motcle';

@Component({
    selector: 'jhi-recherche-update',
    templateUrl: './recherche-update.component.html'
})
export class RechercheUpdateComponent implements OnInit {
    recherche: IRecherche;
    isSaving: boolean;
    ch:string;
    chh:string;
    sources: ISource[];
    listsources: ISource[];
    motcles: IMotcle[];
    selected: any;
    hidden: any;


    constructor(
        protected jhiAlertService: JhiAlertService,
        protected rechercheService: RechercheService,
        protected sourceService: SourceService,
        protected motcleService: MotcleService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ recherche }) => {
            this.recherche = recherche;
        });


        this.sourceService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISource[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISource[]>) => response.body)
            )
            .subscribe((res: ISource[]) => (this.sources = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.motcleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMotcle[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMotcle[]>) => response.body)
            )
            .subscribe((res: IMotcle[]) => (this.motcles = res), (res: HttpErrorResponse) => this.onError(res.message));
    }



    recup(event) {
        this.listsources = event;
    }
    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.recherche.id !== undefined) {

            this.subscribeToSaveResponse(this.rechercheService.update(this.recherche));


        } else {

            for (let i = 0; i < this.listsources.length; i++) {
                this.recherche.source = this.listsources[i];
                this.subscribeToSaveResponse(this.rechercheService.create(this.recherche));
            }
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

    trackSourceById(index: number, item: ISource) {
        return item.id;
    }

    trackMotcleById(index: number, item: IMotcle) {
        return item.id;
    }
}
