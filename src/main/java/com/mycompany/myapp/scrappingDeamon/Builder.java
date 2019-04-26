package com.mycompany.myapp.scrappingDeamon;

import java.util.List;

import com.mycompany.myapp.domain.Recherche;
import com.mycompany.myapp.domain.ResultatRecherche;


public class Builder {Recherche recherche;
    List<NotificationHandler> notifications;

    public Builder setRecherche(Recherche recherche) {
        this.recherche = recherche;
        return this;
    }

    public Builder setNotification(List<NotificationHandler> notifications) {
        this.notifications = notifications;
        return this;
    }

    public Job build(String type) {
        SearchScrappingHandler scrapHandler = new SearchScrappingHandler(recherche);
        SearchRresultHandler searchResultHandler = new SearchRresultHandler(type) {

            @Override
            ResultatRecherche save(ResultatRecherche resultatRecherche) {
                // TODO Auto-generated method stub
                return null;
            }
        };
        return new Job(scrapHandler, notifications, searchResultHandler);

    }


}
