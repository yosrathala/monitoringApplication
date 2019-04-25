/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ProjetTestModule } from '../../../test.module';
import { MotcleUpdateComponent } from 'app/entities/motcle/motcle-update.component';
import { MotcleService } from 'app/entities/motcle/motcle.service';
import { Motcle } from 'app/shared/model/motcle.model';

describe('Component Tests', () => {
    describe('Motcle Management Update Component', () => {
        let comp: MotcleUpdateComponent;
        let fixture: ComponentFixture<MotcleUpdateComponent>;
        let service: MotcleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProjetTestModule],
                declarations: [MotcleUpdateComponent]
            })
                .overrideTemplate(MotcleUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MotcleUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MotcleService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Motcle(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.motcle = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Motcle();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.motcle = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
