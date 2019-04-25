import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMotcle } from 'app/shared/model/motcle.model';
import { MotcleService } from './motcle.service';

@Component({
    selector: 'jhi-motcle-delete-dialog',
    templateUrl: './motcle-delete-dialog.component.html'
})
export class MotcleDeleteDialogComponent {
    motcle: IMotcle;

    constructor(protected motcleService: MotcleService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.motcleService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'motcleListModification',
                content: 'Deleted an motcle'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-motcle-delete-popup',
    template: ''
})
export class MotcleDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ motcle }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MotcleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.motcle = motcle;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/motcle', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/motcle', { outlets: { popup: null } }]);
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
