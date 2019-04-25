/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ProjetTestModule } from '../../../test.module';
import { ResultatRechercheUpdateComponent } from 'app/entities/resultat-recherche/resultat-recherche-update.component';
import { ResultatRechercheService } from 'app/entities/resultat-recherche/resultat-recherche.service';
import { ResultatRecherche } from 'app/shared/model/resultat-recherche.model';

describe('Component Tests', () => {
    describe('ResultatRecherche Management Update Component', () => {
        let comp: ResultatRechercheUpdateComponent;
        let fixture: ComponentFixture<ResultatRechercheUpdateComponent>;
        let service: ResultatRechercheService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProjetTestModule],
                declarations: [ResultatRechercheUpdateComponent]
            })
                .overrideTemplate(ResultatRechercheUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ResultatRechercheUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResultatRechercheService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ResultatRecherche(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.resultatRecherche = entity;
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
                    const entity = new ResultatRecherche();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.resultatRecherche = entity;
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
