package com.mycompany.myapp.scrappingDeamon;

import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.mycompany.myapp.domain.ResultatItem;
import com.mycompany.myapp.domain.ResultatRecherche;
import com.mycompany.myapp.spark.PostInterestManager;
import com.mycompany.myapp.spark.SVMPostInterest;

@ComponentScan(basePackages = "dep.package")
@EnableScheduling
public class Job implements Runnable {

	private SearchScrappingHandler searchHandler;

	private SearchResultHandler searchResultHandler;
	
	private PostInterestManager interestPredictionHandler;

	private List<NotificationHandler> notificationHandlers;
    
	private JobConfig jobConfig;
   
    
    public Integer getPeriodicity() {
		return jobConfig.getPeriodicite();
	}

	public Job(SearchScrappingHandler searchHandler, List<NotificationHandler> notifications,
			SearchResultHandler searchRresultHandler,PostInterestManager predictionHandler, JobConfig jobConfig) {
		this.searchHandler = searchHandler;
		this.notificationHandlers = notifications;
		this.searchResultHandler = searchRresultHandler;
		this.interestPredictionHandler = predictionHandler;
		this.jobConfig = jobConfig;
	}

	@Override
	public void run() {

		ResultatRecherche result = searchHandler.getResult(this.jobConfig);
	 
		if(result.getResultatItems().size() > 0) {
			ResultatRecherche searchResult = searchResultHandler.getNewItems(result, jobConfig);
			if(searchResult.getResultatItems().size() > 0 ) {
				interestPredictionHandler.filterGoodPosts(searchResult);
				searchResultHandler.save(searchResult);
				for (NotificationHandler notificationHandler : notificationHandlers) {
					notificationHandler.send(searchResult.getResultatItems(), jobConfig);
					
				}
			}
			
		}

	}

}
