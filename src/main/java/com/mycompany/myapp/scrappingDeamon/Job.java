package com.mycompany.myapp.scrappingDeamon;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.mycompany.myapp.domain.Recherche;
import com.mycompany.myapp.domain.ResultatItem;
import com.mycompany.myapp.domain.ResultatRecherche;

@ComponentScan(basePackages = "dep.package")
@EnableScheduling
public class Job implements Runnable {

	private SearchScrappingHandler searchHandler;

	private SearchRresultHandler searchResultHandler;

	private List<NotificationHandler> notificationHandlers;
    
    private Recherche search;
   
    
    public Integer getPeriodicity() {
		return search.getPeriodicite();
	}

	public Job(SearchScrappingHandler searchHandler, List<NotificationHandler> notifications,
			SearchRresultHandler searchRresultHandler, Recherche search) {
		this.searchHandler = searchHandler;
		this.notificationHandlers = notifications;
		this.searchResultHandler = searchRresultHandler;
		this.search = search;
	}

	@Override
	public void run() {

		ResultatRecherche result = searchHandler.getResult(this.search);
	 
		if(result.getResultatItems().size() > 0) {
			List<ResultatItem> newItems = searchResultHandler.save(result);
			
			for (NotificationHandler notificationHandler : notificationHandlers) {
				notificationHandler.send(newItems);
				
			}
		}

	}

}
