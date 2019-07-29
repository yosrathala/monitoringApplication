package com.mycompany.myapp.scrappingDeamon;


import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.repository.ResultatItemRepository;
import com.mycompany.myapp.repository.ResultatRechercheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;

import static com.mycompany.myapp.domain.ResultatItem_.resultatRecherche;

@ComponentScan(basePackages = "dep.package")
@EnableScheduling
public class WatchDog <list, result, init> implements Runnable {

    // List <Recherche> recherches;
    SearchScrappingHandler searchHandler;
    private ResultatItemRepository resultatItemRepository;
    private ResultatRechercheRepository resultatRechercheRepository;

    public WatchDog(ResultatRechercheRepository resultatRechercheRepository) {
        super();
        this.resultatRechercheRepository = resultatRechercheRepository;
    }


    static int valeur;

    WatchDog(int val) {
        valeur = val;

    }
    public static WatchDog watchDog;
    private List<Job> jobs;

    TaskScheduler taskScheduler;
    private List<Recherche> rechs;
    private List<NotificationHandler> nots;
    List<Recherche> recherches ;
    List<NotificationHandler> notifications;



  public void init(List<Recherche> recherches, List<NotificationHandler> notifications) {
        rechs = recherches;
        nots = notifications;
        jobs = new ArrayList<>();

        for (int i = 0; i < recherches.size(); i++) {

            Builder jobBuilder = new Builder();
            jobBuilder.setRecherche(recherches.get(i));
            jobBuilder.setNotification(notifications);

            for (Source src : recherches.get(i).getSources()) {
                jobBuilder.setSource(src);
                jobs.add(jobBuilder.build("jdbc"));
            }
            ResultatRecherche resultatRecherche = new ResultatRecherche();
            resultatRecherche.setDate(ZonedDateTime.now());
            resultatRecherche.setRecherche(recherches.get(i));
            resultatRechercheRepository.save(resultatRecherche);

            System.out.println("resulttttaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+ resultatRecherche.toString());



        }

    }





    static synchronized WatchDog getInstance() {
        if (watchDog == null) {
            watchDog = new WatchDog(valeur);
        }
        return watchDog;

    }
    public static void main(String args[]) {
    }
    //ResultatRecherche result = searchHandler.getResult();



   @Override
    public void run() {


       taskScheduler = new ConcurrentTaskScheduler();

       try {
           System.out.println("erere" + jobs.size());

           ResultatRecherche resultatRecherche = new ResultatRecherche();
           System.out.println("resultttta"+ resultatRecherche.toString());
           for (int i = 0; i < jobs.size(); i++) {

               PeriodicTrigger periodicTrigger = new PeriodicTrigger(jobs.get(i).getSearchHandler().getSearch().getPeriodicite(), TimeUnit.SECONDS);

               taskScheduler.schedule(jobs.get(i), periodicTrigger);


           }
       } catch (Exception e) {
           //System.out.println("Interrupted " + e);
           e.printStackTrace();
       }
   }
    }

