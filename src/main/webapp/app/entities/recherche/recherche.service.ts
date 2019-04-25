import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRecherche } from 'app/shared/model/recherche.model';

type EntityResponseType = HttpResponse<IRecherche>;
type EntityArrayResponseType = HttpResponse<IRecherche[]>;

@Injectable({ providedIn: 'root' })
export class RechercheService {
    public resourceUrl = SERVER_API_URL + 'api/recherches';

    constructor(protected http: HttpClient) {}

    create(recherche: IRecherche): Observable<EntityResponseType> {
        return this.http.post<IRecherche>(this.resourceUrl, recherche, { observe: 'response' });
    }

    update(recherche: IRecherche): Observable<EntityResponseType> {
        return this.http.put<IRecherche>(this.resourceUrl, recherche, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRecherche>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRecherche[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
