import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ResultatRecherche } from 'app/shared/model/resultat-recherche.model';
import { ResultatRechercheService } from './resultat-recherche.service';
import { ResultatRechercheComponent } from './resultat-recherche.component';
import { ResultatRechercheDetailComponent } from './resultat-recherche-detail.component';
import { ResultatRechercheUpdateComponent } from './resultat-recherche-update.component';
import { ResultatRechercheDeletePopupComponent } from './resultat-recherche-delete-dialog.component';
import { IResultatRecherche } from 'app/shared/model/resultat-recherche.model';

@Injectable({ providedIn: 'root' })
export class ResultatRechercheResolve implements Resolve<IResultatRecherche> {
    constructor(private service: ResultatRechercheService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IResultatRecherche> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ResultatRecherche>) => response.ok),
                map((resultatRecherche: HttpResponse<ResultatRecherche>) => resultatRecherche.body)
            );
        }
        return of(new ResultatRecherche());
    }
}

export const resultatRechercheRoute: Routes = [
    {
        path: '',
        component: ResultatRechercheComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'projetApp.resultatRecherche.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ResultatRechercheDetailComponent,
        resolve: {
            resultatRecherche: ResultatRechercheResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetApp.resultatRecherche.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ResultatRechercheUpdateComponent,
        resolve: {
            resultatRecherche: ResultatRechercheResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetApp.resultatRecherche.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ResultatRechercheUpdateComponent,
        resolve: {
            resultatRecherche: ResultatRechercheResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetApp.resultatRecherche.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const resultatRecherchePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ResultatRechercheDeletePopupComponent,
        resolve: {
            resultatRecherche: ResultatRechercheResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetApp.resultatRecherche.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
