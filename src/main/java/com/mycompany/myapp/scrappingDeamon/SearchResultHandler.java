package com.mycompany.myapp.scrappingDeamon;

import java.util.Set;

import com.mycompany.myapp.domain.ResultatItem;
import com.mycompany.myapp.domain.ResultatRecherche;

public abstract class SearchResultHandler {

    abstract ResultatRecherche getNewItems (ResultatRecherche resultatRecherche, JobConfig jobConfig);

    abstract Set<ResultatItem> save (ResultatRecherche resultatRecherche );
    
    public SearchResultHandler() {

    }





}
