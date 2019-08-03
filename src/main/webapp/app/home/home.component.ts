import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { LoginModalService, AccountService, Account } from 'app/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { IResultatItem } from 'app/shared/model/resultat-item.model';
import { ResultatItemService } from 'app/entities/resultat-item';
import { Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { ITEMS_PER_PAGE } from 'app/shared';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.css']
})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    currentAccount: any;
    resultatItems: IResultatItem[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    constructor(
        protected resultatItemService: ResultatItemService,
        private accountService: AccountService,
        private loginModalService: LoginModalService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        /*this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });*/
    }

    ngOnInit() {
        this.accountService.identity().then((account: Account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInResultatItems();
    }
    scrap() {
        this.resultatItemService.scaping().subscribe();
    }
    stop() {
        this.resultatItemService.stop().subscribe();
    }
    loadAll() {
        this.resultatItemService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage
                //sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IResultatItem[]>) => this.paginateResultatItems(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }
    /*sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }*/

    protected paginateResultatItems(data: IResultatItem[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.resultatItems = data;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IResultatItem) {
        return item.id;
    }

    registerChangeInResultatItems() {
        this.eventSubscriber = this.eventManager.subscribe('resultatItemListModification', response => this.loadAll());
    }
    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.accountService.identity().then(account => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.accountService.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }
}
