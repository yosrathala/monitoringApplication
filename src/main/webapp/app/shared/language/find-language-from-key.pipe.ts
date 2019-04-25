import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'findLanguageFromKey' })
export class FindLanguageFromKeyPipe implements PipeTransform {
    private languages: any = {
        al: { name: 'Shqip' },
        'ar-ly': { name: 'العربية', rtl: true },
        en: { name: 'English' },
        fr: { name: 'Français' },
        it: { name: 'Italiano' },
        ja: { name: '日本語' },
        es: { name: 'Español' },
        tr: { name: 'Türkçe' }
        // jhipster-needle-i18n-language-key-pipe - JHipster will add/remove languages in this object
    };
    transform(lang: string): string {
        return this.languages[lang].name;
    }
    isRTL(lang: string): boolean {
        return this.languages[lang].rtl;
    }
}
