package com.mycompany.myapp.scrappingDeamon;



import com.mycompany.myapp.domain.Recherche;
import com.mycompany.myapp.domain.ResultatRecherche;

public abstract class SearchScrappingHandler {


    abstract ResultatRecherche getResult(Recherche search);


}
