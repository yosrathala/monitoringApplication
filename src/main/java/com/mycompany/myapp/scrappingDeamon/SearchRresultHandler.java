package com.mycompany.myapp.scrappingDeamon;

import java.util.List;

import com.mycompany.myapp.domain.ResultatItem;
import com.mycompany.myapp.domain.ResultatRecherche;

public abstract class SearchRresultHandler {

    abstract List<ResultatItem> save (ResultatRecherche resultatRecherche);


    public SearchRresultHandler() {

    }





}
