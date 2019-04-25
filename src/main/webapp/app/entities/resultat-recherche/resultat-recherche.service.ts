import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IResultatRecherche } from 'app/shared/model/resultat-recherche.model';

type EntityResponseType = HttpResponse<IResultatRecherche>;
type EntityArrayResponseType = HttpResponse<IResultatRecherche[]>;

@Injectable({ providedIn: 'root' })
export class ResultatRechercheService {
    public resourceUrl = SERVER_API_URL + 'api/resultat-recherches';

    constructor(protected http: HttpClient) {}

    create(resultatRecherche: IResultatRecherche): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(resultatRecherche);
        return this.http
            .post<IResultatRecherche>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(resultatRecherche: IResultatRecherche): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(resultatRecherche);
        return this.http
            .put<IResultatRecherche>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IResultatRecherche>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IResultatRecherche[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(resultatRecherche: IResultatRecherche): IResultatRecherche {
        const copy: IResultatRecherche = Object.assign({}, resultatRecherche, {
            date: resultatRecherche.date != null && resultatRecherche.date.isValid() ? resultatRecherche.date.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.date = res.body.date != null ? moment(res.body.date) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((resultatRecherche: IResultatRecherche) => {
                resultatRecherche.date = resultatRecherche.date != null ? moment(resultatRecherche.date) : null;
            });
        }
        return res;
    }
}
