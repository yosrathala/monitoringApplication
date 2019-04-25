import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ProjetSharedModule } from 'app/shared';
import {
    MotcleComponent,
    MotcleDetailComponent,
    MotcleUpdateComponent,
    MotcleDeletePopupComponent,
    MotcleDeleteDialogComponent,
    motcleRoute,
    motclePopupRoute
} from './';

const ENTITY_STATES = [...motcleRoute, ...motclePopupRoute];

@NgModule({
    imports: [ProjetSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [MotcleComponent, MotcleDetailComponent, MotcleUpdateComponent, MotcleDeleteDialogComponent, MotcleDeletePopupComponent],
    entryComponents: [MotcleComponent, MotcleUpdateComponent, MotcleDeleteDialogComponent, MotcleDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProjetMotcleModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
