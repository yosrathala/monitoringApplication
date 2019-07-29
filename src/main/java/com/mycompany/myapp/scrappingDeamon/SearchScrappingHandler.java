package com.mycompany.myapp.scrappingDeamon;



import com.mycompany.myapp.domain.Recherche;
import com.mycompany.myapp.domain.ResultatItem;
import com.mycompany.myapp.domain.ResultatRecherche;
import com.mycompany.myapp.repository.ResultatItemRepository;

public abstract class SearchScrappingHandler {

    private Recherche search;
    ResultatItemRepository resultatItemRepository;

    public SearchScrappingHandler(Recherche search) {
        super();
        this.search = search;
    }


    abstract ResultatRecherche getResult();


    public Recherche getSearch() {
        return search;
    }
    public void setSearch(Recherche search) {
        this.search = search;
    }


}
