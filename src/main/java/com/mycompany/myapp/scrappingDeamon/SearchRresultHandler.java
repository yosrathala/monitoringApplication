package com.mycompany.myapp.scrappingDeamon;

import com.mycompany.myapp.domain.ResultatRecherche;

public abstract class SearchRresultHandler {
    String type;

    abstract ResultatRecherche save (ResultatRecherche resultatRecherche);

    public SearchRresultHandler(String type) {
        super();
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



}
