package com.mycompany.myapp.scrappingDeamon;



import java.util.List;

import com.mycompany.myapp.domain.Recherche;
import com.mycompany.myapp.domain.Source;
import com.mycompany.myapp.repository.ResultatItemRepository;
import org.springframework.beans.factory.annotation.Autowired;


public class Builder {
    private Source source;

    ResultatItemRepository resultatItemRepository;
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
        if(source.getUrl().contains("rss")) {
            scrapHandler = new RssScrappingHandler(recherche);
        }
        if(source.getNom().equals("facebook")) {
            scrapHandler = new FacebookScrappingHandler(recherche);
        }
        if(source.getNom().equals("progonline")) {
            scrapHandler = new ProgonlineScrappingHandler(recherche);
        }


        SearchRresultHandler searchResultHandler = null;

        if("jdbc".equals(type)) {
            searchResultHandler = new JdbcSave();
        }
        if("jms".equals(type)) {
            searchResultHandler = new JmsSave();
        }

        return new Job(scrapHandler, notifications, searchResultHandler, resultatItemRepository);
    }
}
