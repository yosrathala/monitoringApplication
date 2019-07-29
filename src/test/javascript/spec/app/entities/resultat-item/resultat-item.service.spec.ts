/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { ResultatItemService } from 'app/entities/resultat-item/resultat-item.service';
import { IResultatItem, ResultatItem } from 'app/shared/model/resultat-item.model';

describe('Service Tests', () => {
    describe('ResultatItem Service', () => {
        let injector: TestBed;
        let service: ResultatItemService;
        let httpMock: HttpTestingController;
        let elemDefault: IResultatItem;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ResultatItemService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new ResultatItem(0, 'AAAAAAA', 'AAAAAAA', false, false, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a ResultatItem', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new ResultatItem(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a ResultatItem', async () => {
                const returnedFromService = Object.assign(
                    {
                        contenu: 'BBBBBB',
                        idr: 'BBBBBB',
                        statu: true,
                        note: true,
                        titre: 'BBBBBB',
                        date: 'BBBBBB',
                        url: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of ResultatItem', async () => {
                const returnedFromService = Object.assign(
                    {
                        contenu: 'BBBBBB',
                        idr: 'BBBBBB',
                        statu: true,
                        note: true,
                        titre: 'BBBBBB',
                        date: 'BBBBBB',
                        url: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a ResultatItem', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
