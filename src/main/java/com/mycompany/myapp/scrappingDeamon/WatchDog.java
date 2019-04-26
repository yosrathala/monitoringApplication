package com.mycompany.myapp.scrappingDeamon;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.support.PeriodicTrigger;

import com.mycompany.myapp.domain.Recherche;

@ComponentScan(basePackages = "dep.package")
@EnableScheduling
public class WatchDog implements Runnable {

    // List <Recherche> recherches;
    public WatchDog() {
        super();
    }

    static int valeur;

    WatchDog(int val) {
        valeur = val;
    }

    public static WatchDog watchDog;
    private List<Job> jobs;
    TaskScheduler taskScheduler;

    void init(List<Recherche> recherches, List<NotificationHandler> notifications) {
        for (int i = 0; i < recherches.size(); i++) {
            Builder jobBuilder = new Builder();
            jobBuilder.setRecherche(recherches.get(i));
            jobBuilder.setNotification(notifications);
            jobs.add(jobBuilder.build("JMS"));

        }

    }

    public void run() {
        try {
            for (int i = 0; i < jobs.size(); i++) {

                PeriodicTrigger periodicTrigger = new PeriodicTrigger(2000, TimeUnit.MICROSECONDS);

                taskScheduler.schedule((Runnable) jobs.get(i), periodicTrigger);

            }
        } catch (Exception e) {
            System.out.println("Interrupted" + e);
        }
    }

    static synchronized WatchDog getInstance() {
        if (watchDog == null) {
            return new WatchDog(valeur);
        }
        return watchDog;

    }

}
