/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ProjetTestModule } from '../../../test.module';
import { ResultatItemUpdateComponent } from 'app/entities/resultat-item/resultat-item-update.component';
import { ResultatItemService } from 'app/entities/resultat-item/resultat-item.service';
import { ResultatItem } from 'app/shared/model/resultat-item.model';

describe('Component Tests', () => {
    describe('ResultatItem Management Update Component', () => {
        let comp: ResultatItemUpdateComponent;
        let fixture: ComponentFixture<ResultatItemUpdateComponent>;
        let service: ResultatItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProjetTestModule],
                declarations: [ResultatItemUpdateComponent]
            })
                .overrideTemplate(ResultatItemUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ResultatItemUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResultatItemService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ResultatItem(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.resultatItem = entity;
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
                    const entity = new ResultatItem();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.resultatItem = entity;
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
