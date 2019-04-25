import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ProjetSharedModule } from 'app/shared';
import {
    RechercheComponent,
    RechercheDetailComponent,
    RechercheUpdateComponent,
    RechercheDeletePopupComponent,
    RechercheDeleteDialogComponent,
    rechercheRoute,
    recherchePopupRoute
} from './';

const ENTITY_STATES = [...rechercheRoute, ...recherchePopupRoute];

@NgModule({
    imports: [ProjetSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RechercheComponent,
        RechercheDetailComponent,
        RechercheUpdateComponent,
        RechercheDeleteDialogComponent,
        RechercheDeletePopupComponent
    ],
    entryComponents: [RechercheComponent, RechercheUpdateComponent, RechercheDeleteDialogComponent, RechercheDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProjetRechercheModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
