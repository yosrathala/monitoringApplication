package com.mycompany.myapp.scrappingDeamon;



import java.util.Arrays;

import com.mycompany.myapp.domain.ResultatRecherche;

public abstract class SearchScrappingHandler {


    abstract ResultatRecherche getResult(JobConfig jobConfig);
    
    protected static boolean stringContainsItems(String inputStr, String[] items) {
        return Arrays.stream(items).parallel().anyMatch(inputStr::contains);
    }


}
