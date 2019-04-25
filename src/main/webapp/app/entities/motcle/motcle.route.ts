import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Motcle } from 'app/shared/model/motcle.model';
import { MotcleService } from './motcle.service';
import { MotcleComponent } from './motcle.component';
import { MotcleDetailComponent } from './motcle-detail.component';
import { MotcleUpdateComponent } from './motcle-update.component';
import { MotcleDeletePopupComponent } from './motcle-delete-dialog.component';
import { IMotcle } from 'app/shared/model/motcle.model';

@Injectable({ providedIn: 'root' })
export class MotcleResolve implements Resolve<IMotcle> {
    constructor(private service: MotcleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMotcle> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Motcle>) => response.ok),
                map((motcle: HttpResponse<Motcle>) => motcle.body)
            );
        }
        return of(new Motcle());
    }
}

export const motcleRoute: Routes = [
    {
        path: '',
        component: MotcleComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'projetApp.motcle.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MotcleDetailComponent,
        resolve: {
            motcle: MotcleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetApp.motcle.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MotcleUpdateComponent,
        resolve: {
            motcle: MotcleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetApp.motcle.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MotcleUpdateComponent,
        resolve: {
            motcle: MotcleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetApp.motcle.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const motclePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MotcleDeletePopupComponent,
        resolve: {
            motcle: MotcleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'projetApp.motcle.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
