import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMotcle } from 'app/shared/model/motcle.model';

type EntityResponseType = HttpResponse<IMotcle>;
type EntityArrayResponseType = HttpResponse<IMotcle[]>;

@Injectable({ providedIn: 'root' })
export class MotcleService {
    public resourceUrl = SERVER_API_URL + 'api/motcles';

    constructor(protected http: HttpClient) {}

    create(motcle: IMotcle): Observable<EntityResponseType> {
        return this.http.post<IMotcle>(this.resourceUrl, motcle, { observe: 'response' });
    }

    update(motcle: IMotcle): Observable<EntityResponseType> {
        return this.http.put<IMotcle>(this.resourceUrl, motcle, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMotcle>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMotcle[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
