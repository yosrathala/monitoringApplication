import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ProjetSharedModule } from 'app/shared';
import {
    ResultatRechercheComponent,
    ResultatRechercheDetailComponent,
    ResultatRechercheUpdateComponent,
    ResultatRechercheDeletePopupComponent,
    ResultatRechercheDeleteDialogComponent,
    resultatRechercheRoute,
    resultatRecherchePopupRoute
} from './';

const ENTITY_STATES = [...resultatRechercheRoute, ...resultatRecherchePopupRoute];

@NgModule({
    imports: [ProjetSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ResultatRechercheComponent,
        ResultatRechercheDetailComponent,
        ResultatRechercheUpdateComponent,
        ResultatRechercheDeleteDialogComponent,
        ResultatRechercheDeletePopupComponent
    ],
    entryComponents: [
        ResultatRechercheComponent,
        ResultatRechercheUpdateComponent,
        ResultatRechercheDeleteDialogComponent,
        ResultatRechercheDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProjetResultatRechercheModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
