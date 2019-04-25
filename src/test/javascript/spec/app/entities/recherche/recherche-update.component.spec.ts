/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ProjetTestModule } from '../../../test.module';
import { RechercheUpdateComponent } from 'app/entities/recherche/recherche-update.component';
import { RechercheService } from 'app/entities/recherche/recherche.service';
import { Recherche } from 'app/shared/model/recherche.model';

describe('Component Tests', () => {
    describe('Recherche Management Update Component', () => {
        let comp: RechercheUpdateComponent;
        let fixture: ComponentFixture<RechercheUpdateComponent>;
        let service: RechercheService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProjetTestModule],
                declarations: [RechercheUpdateComponent]
            })
                .overrideTemplate(RechercheUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RechercheUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RechercheService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Recherche(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.recherche = entity;
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
                    const entity = new Recherche();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.recherche = entity;
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
