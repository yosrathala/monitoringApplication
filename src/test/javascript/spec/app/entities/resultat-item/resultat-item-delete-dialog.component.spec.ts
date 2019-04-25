/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ProjetTestModule } from '../../../test.module';
import { ResultatItemDeleteDialogComponent } from 'app/entities/resultat-item/resultat-item-delete-dialog.component';
import { ResultatItemService } from 'app/entities/resultat-item/resultat-item.service';

describe('Component Tests', () => {
    describe('ResultatItem Management Delete Component', () => {
        let comp: ResultatItemDeleteDialogComponent;
        let fixture: ComponentFixture<ResultatItemDeleteDialogComponent>;
        let service: ResultatItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProjetTestModule],
                declarations: [ResultatItemDeleteDialogComponent]
            })
                .overrideTemplate(ResultatItemDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ResultatItemDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResultatItemService);
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
