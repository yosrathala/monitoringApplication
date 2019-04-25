/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProjetTestModule } from '../../../test.module';
import { MotcleDetailComponent } from 'app/entities/motcle/motcle-detail.component';
import { Motcle } from 'app/shared/model/motcle.model';

describe('Component Tests', () => {
    describe('Motcle Management Detail Component', () => {
        let comp: MotcleDetailComponent;
        let fixture: ComponentFixture<MotcleDetailComponent>;
        const route = ({ data: of({ motcle: new Motcle(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProjetTestModule],
                declarations: [MotcleDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MotcleDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MotcleDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.motcle).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
