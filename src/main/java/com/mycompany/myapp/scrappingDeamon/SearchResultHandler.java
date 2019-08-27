package com.mycompany.myapp.scrappingDeamon;

import java.util.List;

import com.mycompany.myapp.domain.ResultatItem;
import com.mycompany.myapp.domain.ResultatRecherche;

public abstract class SearchResultHandler {

    abstract List<ResultatItem> save (ResultatRecherche resultatRecherche, JobConfig jobConfig);


    public SearchResultHandler() {

    }





}
