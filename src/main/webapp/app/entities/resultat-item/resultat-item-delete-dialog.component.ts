import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResultatItem } from 'app/shared/model/resultat-item.model';
import { ResultatItemService } from './resultat-item.service';

@Component({
    selector: 'jhi-resultat-item-delete-dialog',
    templateUrl: './resultat-item-delete-dialog.component.html'
})
export class ResultatItemDeleteDialogComponent {
    resultatItem: IResultatItem;

    constructor(
        protected resultatItemService: ResultatItemService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.resultatItemService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'resultatItemListModification',
                content: 'Deleted an resultatItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-resultat-item-delete-popup',
    template: ''
})
export class ResultatItemDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ resultatItem }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ResultatItemDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.resultatItem = resultatItem;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/resultat-item', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/resultat-item', { outlets: { popup: null } }]);
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
