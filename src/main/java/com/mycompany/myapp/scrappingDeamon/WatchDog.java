package com.mycompany.myapp.scrappingDeamon;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.Recherche;
import com.mycompany.myapp.domain.Source;

@Service
public class WatchDog {



    private JdbcSave jdbcSave ;
    private List<Job> jobs;
    TaskScheduler taskScheduler;
    List<Recherche> recherches ;
    List<NotificationHandler> notifications;
    @Autowired
    private ApplicationContext context;


  public void init(List<Recherche> recherches, List<NotificationHandler> notifications) {

        jobs = new ArrayList<>();

        for (int i = 0; i < recherches.size(); i++) {

            Builder jobBuilder = new Builder();
            jdbcSave = context.getBean(JdbcSave.class);
            jobBuilder.setSearchResultHandler(jdbcSave);
            jobBuilder.setRecherche(recherches.get(i));
            jobBuilder.setNotification(notifications);

            for (Source src : recherches.get(i).getSources()) {
                jobBuilder.setSource(src);
                jobs.add(jobBuilder.build("jdbc"));
            }
        }

    }

   
    public void run() {


       taskScheduler = new ConcurrentTaskScheduler();

       try {

           for (int i = 0; i < jobs.size(); i++) {

               PeriodicTrigger periodicTrigger = new PeriodicTrigger(jobs.get(i).getSearchHandler().getSearch().getPeriodicite(), TimeUnit.SECONDS);

               taskScheduler.schedule(jobs.get(i), periodicTrigger);


           }
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
    }

