import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Source } from 'app/shared/model/source.model';
import { SourceService } from './source.service';
import { SourceComponent } from './source.component';
import { SourceDetailComponent } from './source-detail.component';
import { SourceUpdateComponent } from './source-update.component';
import { SourceDeletePopupComponent } from './source-delete-dialog.component';
import { ISource } from 'app/shared/model/source.model';

@Injectable({ providedIn: 'root' })
export class SourceResolve implements Resolve<ISource> {
    constructor(private service: SourceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISource> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Source>) => response.ok),
                map((source: HttpResponse<Source>) => source.body)
            );
        }
        return of(new Source());
    }
}

export const sourceRoute: Routes = [
    {
        path: '',
        component: SourceComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'projetApp.source.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SourceDetailComponent,
        resolve: {
            source: SourceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetApp.source.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SourceUpdateComponent,
        resolve: {
            source: SourceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetApp.source.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SourceUpdateComponent,
        resolve: {
            source: SourceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetApp.source.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sourcePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SourceDeletePopupComponent,
        resolve: {
            source: SourceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetApp.source.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
