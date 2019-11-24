package com.mycompany.myapp.scrappingDeamon;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.spark.mllib.classification.SVMModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.Recherche;
import com.mycompany.myapp.domain.Source;
import com.mycompany.myapp.spark.PostInterestManager;
import com.mycompany.myapp.spark.SVMPostInterest;

@Service
public class WatchDog {

    private List<Job> jobs = new ArrayList<>();
    private TaskScheduler taskScheduler = new ConcurrentTaskScheduler();
    ;
    private List<Recherche> recherches;
    private List<ScheduledFuture> futureList = new ArrayList();
    @Autowired
    private ApplicationContext context;
    

    public void init(List<Recherche> recherches) {
    	HandlerFactory.initContext(context);
    	
    	PostInterestManager predictionHandler = HandlerFactory.getPredictionHnadler("rf");
    	
    	if(!predictionHandler.isModelSet()) {
    		String file = "/home/bji/workspace/projetappmonitoring/sparkBase.csv";
    		predictionHandler.init(file);
    		predictionHandler.buildModel();
    		
    	}
		
        for (Recherche search : recherches) {
            if (search.isActivated()) {
                for (Source source : search.getSources()) {
                    Builder jobBuilder = new Builder();
                    jobBuilder.setSearchResultDestination("jdbc");
                    jobBuilder.setRecherche(search);
                    jobBuilder.setSource(source);
                    jobs.add(jobBuilder.build());
                }
            }

        }
    }

    public void run() {


        try {

            for (Job job : jobs) {
                PeriodicTrigger periodicTrigger = new PeriodicTrigger(job.getPeriodicity(), TimeUnit.MINUTES);
                ScheduledFuture<?> future = taskScheduler.schedule(job, periodicTrigger);
                futureList.add(future);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void stopAll() {
        for (ScheduledFuture scheduledFuture : futureList) {
            scheduledFuture.cancel(false);
        }
        futureList.clear();
    }

    public boolean isRuning() {
        if (futureList.size() == 0) {
            return false;
        } else {
            return true;
        }
    }
}
