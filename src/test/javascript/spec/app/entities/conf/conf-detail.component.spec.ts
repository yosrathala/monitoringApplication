/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProjetTestModule } from '../../../test.module';
import { ConfDetailComponent } from 'app/entities/conf/conf-detail.component';
import { Conf } from 'app/shared/model/conf.model';

describe('Component Tests', () => {
    describe('Conf Management Detail Component', () => {
        let comp: ConfDetailComponent;
        let fixture: ComponentFixture<ConfDetailComponent>;
        const route = ({ data: of({ conf: new Conf(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ProjetTestModule],
                declarations: [ConfDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ConfDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ConfDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.conf).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
