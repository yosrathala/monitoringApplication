package com.mycompany.myapp.scrappingDeamon;

import com.mycompany.myapp.domain.Recherche;
import com.mycompany.myapp.domain.ResultatRecherche;

public class SearchScrappingHandler {

    private Recherche search;


    public SearchScrappingHandler(Recherche search) {
        super();
        this.search = search;
    }


    ResultatRecherche getResult() {
        return null;

    }
    public Recherche getSearch() {
        return search;
    }
    public void setSearch(Recherche search) {
        this.search = search;
    }


}
