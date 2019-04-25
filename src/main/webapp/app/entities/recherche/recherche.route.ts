import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Recherche } from 'app/shared/model/recherche.model';
import { RechercheService } from './recherche.service';
import { RechercheComponent } from './recherche.component';
import { RechercheDetailComponent } from './recherche-detail.component';
import { RechercheUpdateComponent } from './recherche-update.component';
import { RechercheDeletePopupComponent } from './recherche-delete-dialog.component';
import { IRecherche } from 'app/shared/model/recherche.model';

@Injectable({ providedIn: 'root' })
export class RechercheResolve implements Resolve<IRecherche> {
    constructor(private service: RechercheService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRecherche> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Recherche>) => response.ok),
                map((recherche: HttpResponse<Recherche>) => recherche.body)
            );
        }
        return of(new Recherche());
    }
}

export const rechercheRoute: Routes = [
    {
        path: '',
        component: RechercheComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'projetApp.recherche.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RechercheDetailComponent,
        resolve: {
            recherche: RechercheResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetApp.recherche.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RechercheUpdateComponent,
        resolve: {
            recherche: RechercheResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetApp.recherche.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RechercheUpdateComponent,
        resolve: {
            recherche: RechercheResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetApp.recherche.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const recherchePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RechercheDeletePopupComponent,
        resolve: {
            recherche: RechercheResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetApp.recherche.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
