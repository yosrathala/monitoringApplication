import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IConf } from 'app/shared/model/conf.model';

type EntityResponseType = HttpResponse<IConf>;
type EntityArrayResponseType = HttpResponse<IConf[]>;

@Injectable({ providedIn: 'root' })
export class ConfService {
    public resourceUrl = SERVER_API_URL + 'api/confs';

    constructor(protected http: HttpClient) {}

    create(conf: IConf): Observable<EntityResponseType> {
        return this.http.post<IConf>(this.resourceUrl, conf, { observe: 'response' });
    }

    update(conf: IConf): Observable<EntityResponseType> {
        return this.http.put<IConf>(this.resourceUrl, conf, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IConf>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IConf[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
