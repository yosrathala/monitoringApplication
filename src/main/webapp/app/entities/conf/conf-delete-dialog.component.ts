import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConf } from 'app/shared/model/conf.model';
import { ConfService } from './conf.service';

@Component({
    selector: 'jhi-conf-delete-dialog',
    templateUrl: './conf-delete-dialog.component.html'
})
export class ConfDeleteDialogComponent {
    conf: IConf;

    constructor(protected confService: ConfService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.confService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'confListModification',
                content: 'Deleted an conf'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-conf-delete-popup',
    template: ''
})
export class ConfDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ conf }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ConfDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.conf = conf;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/conf', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/conf', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
