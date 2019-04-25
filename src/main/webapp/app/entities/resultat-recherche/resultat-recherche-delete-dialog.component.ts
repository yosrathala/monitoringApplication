import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResultatRecherche } from 'app/shared/model/resultat-recherche.model';
import { ResultatRechercheService } from './resultat-recherche.service';

@Component({
    selector: 'jhi-resultat-recherche-delete-dialog',
    templateUrl: './resultat-recherche-delete-dialog.component.html'
})
export class ResultatRechercheDeleteDialogComponent {
    resultatRecherche: IResultatRecherche;

    constructor(
        protected resultatRechercheService: ResultatRechercheService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.resultatRechercheService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'resultatRechercheListModification',
                content: 'Deleted an resultatRecherche'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-resultat-recherche-delete-popup',
    template: ''
})
export class ResultatRechercheDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ resultatRecherche }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ResultatRechercheDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.resultatRecherche = resultatRecherche;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/resultat-recherche', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/resultat-recherche', { outlets: { popup: null } }]);
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
