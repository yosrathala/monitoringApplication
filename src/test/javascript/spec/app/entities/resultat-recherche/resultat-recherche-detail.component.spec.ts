/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProjetTestModule } from '../../../test.module';
import { ResultatRechercheDetailComponent } from 'app/entities/resultat-recherche/resultat-recherche-detail.component';
import { ResultatRecherche } from 'app/shared/model/resultat-recherche.model';

describe('Component Tests', () => {
    describe('ResultatRecherche Management Detail Component', () => {
        let comp: ResultatRechercheDetailComponent;
        let fixture: ComponentFixture<ResultatRechercheDetailComponent>;
        const route = ({ data: of({ resultatRecherche: new ResultatRecherche(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProjetTestModule],
                declarations: [ResultatRechercheDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ResultatRechercheDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ResultatRechercheDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.resultatRecherche).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
