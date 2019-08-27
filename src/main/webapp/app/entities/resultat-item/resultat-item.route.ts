import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ResultatItem } from 'app/shared/model/resultat-item.model';
import { ResultatItemService } from './resultat-item.service';
import { ResultatItemComponent } from './resultat-item.component';
import { ResultatItemDetailComponent } from './resultat-item-detail.component';
import { ResultatItemUpdateComponent } from './resultat-item-update.component';
import { ResultatItemDeletePopupComponent } from './resultat-item-delete-dialog.component';
import { IResultatItem } from 'app/shared/model/resultat-item.model';

@Injectable({ providedIn: 'root' })
export class ResultatItemResolve implements Resolve<IResultatItem> {
    constructor(private service: ResultatItemService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IResultatItem> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ResultatItem>) => response.ok),
                map((resultatItem: HttpResponse<ResultatItem>) => resultatItem.body)
            );
        }
        return of(new ResultatItem());
    }
}

export const resultatItemRoute: Routes = [
    {
        path: '',
        component: ResultatItemComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            //authorities: ['ROLE_USER'],
            defaultSort: 'id,desc',
            pageTitle: 'projetApp.resultatItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ResultatItemDetailComponent,
        resolve: {
            resultatItem: ResultatItemResolve
        },
        data: {
            //authorities: ['ROLE_USER'],
            pageTitle: 'projetApp.resultatItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ResultatItemUpdateComponent,
        resolve: {
            resultatItem: ResultatItemResolve
        },
        data: {
            //authorities: ['ROLE_USER'],
            pageTitle: 'projetApp.resultatItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ResultatItemUpdateComponent,
        resolve: {
            resultatItem: ResultatItemResolve
        },
        data: {
            //authorities: ['ROLE_USER'],
            pageTitle: 'projetApp.resultatItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const resultatItemPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ResultatItemDeletePopupComponent,
        resolve: {
            resultatItem: ResultatItemResolve
        },
        data: {
            //authorities: ['ROLE_USER'],
            pageTitle: 'projetApp.resultatItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
