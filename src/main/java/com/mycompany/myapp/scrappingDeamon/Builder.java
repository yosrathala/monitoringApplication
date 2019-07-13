package com.mycompany.myapp.scrappingDeamon;

import java.util.List;

import com.mycompany.myapp.domain.Recherche;
;
import com.mycompany.myapp.domain.Source;

public class Builder {
    private Source source;
    Recherche recherche;
    List<NotificationHandler> notifications;

    public Builder setRecherche(Recherche recherche) {
        this.recherche = recherche;
        return this;
    }

    public Builder setSource(Source source) {
        this.source = source;
        return this;
    }

    public Builder setNotification(List<NotificationHandler> notifications) {
        this.notifications = notifications;
        return this;
    }

    public Job build(String type) {
        SearchScrappingHandler scrapHandler =null;
        if(source.getNom().equals("linkedin")) {
            scrapHandler = new LinkedScappingHandler(recherche);
        }


        SearchRresultHandler searchResultHandler = null;

        if("jdbc".equals(type)) {
            searchResultHandler = new JdbcSave();
        }
        if("jms".equals(type)) {
            searchResultHandler = new JmsSave();
        }

        return new Job(scrapHandler, notifications, searchResultHandler);
    }
}
