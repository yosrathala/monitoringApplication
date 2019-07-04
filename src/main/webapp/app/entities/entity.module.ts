import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'user-extra',
                loadChildren: './user-extra/user-extra.module#ProjetUserExtraModule'
            },
            {
                path: 'source',
                loadChildren: './source/source.module#ProjetSourceModule'
            },
            {
                path: 'motcle',
                loadChildren: './motcle/motcle.module#ProjetMotcleModule'
            },
            {
                path: 'resultat-recherche',
                loadChildren: './resultat-recherche/resultat-recherche.module#ProjetResultatRechercheModule'
            },
            {
                path: 'recherche',
                loadChildren: './recherche/recherche.module#ProjetRechercheModule'
            },
            {
                path: 'resultat-item',
                loadChildren: './resultat-item/resultat-item.module#ProjetResultatItemModule'
            },
            {
                path: 'conf',
                loadChildren: './conf/conf.module#ProjetConfModule'
            },
            {
                path: 'recherche',
                loadChildren: './recherche/recherche.module#ProjetRechercheModule'
            },
            {
                path: 'resultat-item',
                loadChildren: './resultat-item/resultat-item.module#ProjetResultatItemModule'
            },
            {
                path: 'resultat-item',
                loadChildren: './resultat-item/resultat-item.module#ProjetResultatItemModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProjetEntityModule {}
