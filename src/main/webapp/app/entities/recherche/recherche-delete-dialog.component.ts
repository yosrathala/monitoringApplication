import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRecherche } from 'app/shared/model/recherche.model';
import { RechercheService } from './recherche.service';

@Component({
    selector: 'jhi-recherche-delete-dialog',
    templateUrl: './recherche-delete-dialog.component.html'
})
export class RechercheDeleteDialogComponent {
    recherche: IRecherche;

    constructor(
        protected rechercheService: RechercheService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rechercheService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rechercheListModification',
                content: 'Deleted an recherche'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-recherche-delete-popup',
    template: ''
})
export class RechercheDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ recherche }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RechercheDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.recherche = recherche;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/recherche', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/recherche', { outlets: { popup: null } }]);
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
