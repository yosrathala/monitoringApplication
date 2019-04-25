/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ProjetTestModule } from '../../../test.module';
import { RechercheDeleteDialogComponent } from 'app/entities/recherche/recherche-delete-dialog.component';
import { RechercheService } from 'app/entities/recherche/recherche.service';

describe('Component Tests', () => {
    describe('Recherche Management Delete Component', () => {
        let comp: RechercheDeleteDialogComponent;
        let fixture: ComponentFixture<RechercheDeleteDialogComponent>;
        let service: RechercheService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProjetTestModule],
                declarations: [RechercheDeleteDialogComponent]
            })
                .overrideTemplate(RechercheDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RechercheDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RechercheService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
