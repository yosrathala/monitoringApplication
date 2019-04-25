/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProjetTestModule } from '../../../test.module';
import { ResultatItemDetailComponent } from 'app/entities/resultat-item/resultat-item-detail.component';
import { ResultatItem } from 'app/shared/model/resultat-item.model';

describe('Component Tests', () => {
    describe('ResultatItem Management Detail Component', () => {
        let comp: ResultatItemDetailComponent;
        let fixture: ComponentFixture<ResultatItemDetailComponent>;
        const route = ({ data: of({ resultatItem: new ResultatItem(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProjetTestModule],
                declarations: [ResultatItemDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ResultatItemDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ResultatItemDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.resultatItem).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
