import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ProjetSharedModule } from 'app/shared';
import {
    ConfComponent,
    ConfDetailComponent,
    ConfUpdateComponent,
    ConfDeletePopupComponent,
    ConfDeleteDialogComponent,
    confRoute,
    confPopupRoute
} from './';

const ENTITY_STATES = [...confRoute, ...confPopupRoute];

@NgModule({
    imports: [ProjetSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ConfComponent, ConfDetailComponent, ConfUpdateComponent, ConfDeleteDialogComponent, ConfDeletePopupComponent],
    entryComponents: [ConfComponent, ConfUpdateComponent, ConfDeleteDialogComponent, ConfDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProjetConfModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
