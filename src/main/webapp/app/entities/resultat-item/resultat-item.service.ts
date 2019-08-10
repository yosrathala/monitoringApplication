import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IResultatItem } from 'app/shared/model/resultat-item.model';

type EntityResponseType = HttpResponse<IResultatItem>;
type EntityArrayResponseType = HttpResponse<IResultatItem[]>;

@Injectable({ providedIn: 'root' })
export class ResultatItemService {
    public resourceUrl = SERVER_API_URL + 'api/resultat-items';
    public resourceUrls = SERVER_API_URL + 'api/scrapping';
    public resourceUrlstop = SERVER_API_URL + 'api/stop';

    constructor(protected http: HttpClient) {}

    create(resultatItem: IResultatItem): Observable<EntityResponseType> {
        return this.http.post<IResultatItem>(this.resourceUrl, resultatItem, { observe: 'response' });
    }

    update(resultatItem: IResultatItem): Observable<EntityResponseType> {
        return this.http.put<IResultatItem>(this.resourceUrl, resultatItem, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IResultatItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IResultatItem[]>(this.resourceUrl, { params: options, observe: 'response' });
    }
    scaping(): Observable<any> {
        return this.http.get(`${this.resourceUrls}`, { observe: 'response' });
    }
    stop(): Observable<any> {
        return this.http.get(`${this.resourceUrlstop}`, { observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
