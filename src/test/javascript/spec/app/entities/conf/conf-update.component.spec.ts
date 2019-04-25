/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ProjetTestModule } from '../../../test.module';
import { ConfUpdateComponent } from 'app/entities/conf/conf-update.component';
import { ConfService } from 'app/entities/conf/conf.service';
import { Conf } from 'app/shared/model/conf.model';

describe('Component Tests', () => {
    describe('Conf Management Update Component', () => {
        let comp: ConfUpdateComponent;
        let fixture: ComponentFixture<ConfUpdateComponent>;
        let service: ConfService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProjetTestModule],
                declarations: [ConfUpdateComponent]
            })
                .overrideTemplate(ConfUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ConfUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConfService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Conf(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.conf = entity;
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
                    const entity = new Conf();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.conf = entity;
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
