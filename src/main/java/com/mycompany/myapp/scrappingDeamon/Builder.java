package com.mycompany.myapp.scrappingDeamon;



import java.util.ArrayList;
import java.util.List;

import com.mycompany.myapp.domain.Recherche;
import com.mycompany.myapp.domain.Source;


public class Builder {
	
    private Source source;
    private Recherche recherche;
    private String setSearchResultDestination;

	public Builder setRecherche(Recherche recherche) {
        this.recherche = recherche;
        return this;
    }

    public Builder setSource(Source source) {
        this.source = source;
        return this;
    }
    
	public Builder setSearchResultDestination(String searchResultDestination) {
		this.setSearchResultDestination = searchResultDestination;
		return this;
	}

    public Job build() {
        List<NotificationHandler> notificationsHandlers = new ArrayList<>();
        SearchScrappingHandler scrapHandler = HandlerFactory.getSearchHandler(this.source.getNom());
    	SearchRresultHandler searchResultHandler = HandlerFactory.getSearchResultHandler(this.setSearchResultDestination);
    	
    	if(recherche.isEmailnotif()) {
    		notificationsHandlers.add(HandlerFactory.getNotificationHandler("email"));
    	}
    	if(recherche.isSmsnotif()) {
    		notificationsHandlers.add(HandlerFactory.getNotificationHandler("sms"));
    	}
    	if(recherche.isPushnotif()) {
    		notificationsHandlers.add(HandlerFactory.getNotificationHandler("push"));
    	}
    	
        return new Job(scrapHandler, notificationsHandlers, searchResultHandler, recherche);
    }


}
