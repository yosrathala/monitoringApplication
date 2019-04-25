/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ProjetTestModule } from '../../../test.module';
import { MotcleDeleteDialogComponent } from 'app/entities/motcle/motcle-delete-dialog.component';
import { MotcleService } from 'app/entities/motcle/motcle.service';

describe('Component Tests', () => {
    describe('Motcle Management Delete Component', () => {
        let comp: MotcleDeleteDialogComponent;
        let fixture: ComponentFixture<MotcleDeleteDialogComponent>;
        let service: MotcleService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProjetTestModule],
                declarations: [MotcleDeleteDialogComponent]
            })
                .overrideTemplate(MotcleDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MotcleDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MotcleService);
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
