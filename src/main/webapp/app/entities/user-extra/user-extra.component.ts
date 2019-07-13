import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUserExtra } from 'app/shared/model/user-extra.model';
import { AccountService, UserService } from 'app/core';
import { UserExtraService } from './user-extra.service';

@Component({
    selector: 'jhi-user-extra',
    templateUrl: './user-extra.component.html'
})
export class UserExtraComponent implements OnInit, OnDestroy {
    userExtras: IUserExtra[] = [];
    currentAccount: any;
    eventSubscriber: Subscription;
    error: any;
    success: any;
    constructor(
        protected userExtraService: UserExtraService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService,
        protected userService: UserService
    ) {}

    loadAll() {
        this.userExtraService
            .query()
            .pipe(
                filter((res: HttpResponse<IUserExtra[]>) => res.ok),
                map((res: HttpResponse<IUserExtra[]>) => res.body)
            )
            .subscribe(
                (res: IUserExtra[]) => {
                    this.userExtras = res;
                    console.log('fffffffffff' + this.userExtras.values());
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInUserExtras();
    }
    setActive(user, isActivated) {
        user.activated = isActivated;

        this.userService.update(user).subscribe(response => {
            if (response.status === 200) {
                this.error = null;
                this.success = 'OK';
                this.loadAll();
            } else {
                this.success = null;
                this.error = 'ERROR';
            }
        });
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IUserExtra) {
        return item.id;
    }

    registerChangeInUserExtras() {
        this.eventSubscriber = this.eventManager.subscribe('userExtraListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
