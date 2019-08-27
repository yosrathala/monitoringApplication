package com.mycompany.myapp.scrappingDeamon;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.mycompany.myapp.domain.ResultatItem;
import com.mycompany.myapp.domain.ResultatRecherche;

@ComponentScan(basePackages = "dep.package")
@EnableScheduling
public class Job implements Runnable {

	private SearchScrappingHandler searchHandler;

	private SearchResultHandler searchResultHandler;

	private List<NotificationHandler> notificationHandlers;
    
	private JobConfig jobConfig;
   
    
    public Integer getPeriodicity() {
		return jobConfig.getPeriodicite();
	}

	public Job(SearchScrappingHandler searchHandler, List<NotificationHandler> notifications,
			SearchResultHandler searchRresultHandler, JobConfig jobConfig) {
		this.searchHandler = searchHandler;
		this.notificationHandlers = notifications;
		this.searchResultHandler = searchRresultHandler;
		this.jobConfig = jobConfig;
	}

	@Override
	public void run() {

		ResultatRecherche result = searchHandler.getResult(this.jobConfig);
	 
		if(result.getResultatItems().size() > 0) {
			List<ResultatItem> newItems = searchResultHandler.save(result, jobConfig);
			
			for (NotificationHandler notificationHandler : notificationHandlers) {
				notificationHandler.send(newItems);
				
			}
		}

	}

}
