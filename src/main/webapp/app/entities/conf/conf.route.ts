import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Conf } from 'app/shared/model/conf.model';
import { ConfService } from './conf.service';
import { ConfComponent } from './conf.component';
import { ConfDetailComponent } from './conf-detail.component';
import { ConfUpdateComponent } from './conf-update.component';
import { ConfDeletePopupComponent } from './conf-delete-dialog.component';
import { IConf } from 'app/shared/model/conf.model';

@Injectable({ providedIn: 'root' })
export class ConfResolve implements Resolve<IConf> {
    constructor(private service: ConfService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IConf> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Conf>) => response.ok),
                map((conf: HttpResponse<Conf>) => conf.body)
            );
        }
        return of(new Conf());
    }
}

export const confRoute: Routes = [
    {
        path: '',
        component: ConfComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'projetApp.conf.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ConfDetailComponent,
        resolve: {
            conf: ConfResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetApp.conf.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ConfUpdateComponent,
        resolve: {
            conf: ConfResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetApp.conf.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ConfUpdateComponent,
        resolve: {
            conf: ConfResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetApp.conf.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const confPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ConfDeletePopupComponent,
        resolve: {
            conf: ConfResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetApp.conf.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
