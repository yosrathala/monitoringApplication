package com.mycompany.myapp.scrappingDeamon;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.mycompany.myapp.domain.Recherche;
import com.mycompany.myapp.domain.ResultatItem;
import com.mycompany.myapp.domain.ResultatRecherche;
import com.mycompany.myapp.domain.Source;
import com.mycompany.myapp.repository.RechercheRepository;
import com.mycompany.myapp.repository.ResultatItemRepository;
import com.mycompany.myapp.repository.ResultatRechercheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;

import static com.sun.deploy.util.SessionState.save;
@ComponentScan(basePackages = "dep.package")
@EnableScheduling
public class Job implements Runnable {
    //implements Runnable
    SearchScrappingHandler searchHandler;

    SearchRresultHandler searchRresultHandler;
    private JdbcSave jdbcSave;
    private ResultatItemRepository resultatItemRepository;

    ResultatRechercheRepository resultatRechercheRepository;

    List<NotificationHandler> notifications = null;

    public Job(RechercheRepository rechercheRepository, ResultatItemRepository resultatItemRepository) {

        this.resultatItemRepository = resultatItemRepository;
        recherches = rechercheRepository.findAllWithEagerRelationships();
    }

    List<Recherche> recherches = new ArrayList<>();


    public Job(SearchScrappingHandler searchHandler, List<NotificationHandler> notifications,
               SearchRresultHandler searchRresultHandler, ResultatItemRepository resultatItemRepository) {
        super();
        this.resultatItemRepository = resultatItemRepository;
        this.searchHandler = searchHandler;
        this.notifications = notifications;
        this.searchRresultHandler = searchRresultHandler;
    }

    public Job(ResultatRechercheRepository rechercheRepository) {
        this.resultatRechercheRepository = rechercheRepository;
    }

    public SearchScrappingHandler getSearchHandler() {
        return searchHandler;
    }

    public void setSearchHandler(SearchScrappingHandler searchHandler) {
        this.searchHandler = searchHandler;
    }

    public List<NotificationHandler> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationHandler> notifications) {
        this.notifications = notifications;
    }

    public SearchRresultHandler getSearchRresultHandler() {
        return searchRresultHandler;
    }

    public void setSearchRresultHandler(SearchRresultHandler searchRresultHandler) {
        this.searchRresultHandler = searchRresultHandler;
    }



    @Override
    public void run() {
        // TODO Auto-generated method stub
     WatchDog w=new WatchDog(resultatRechercheRepository);

        ResultatRecherche result = searchHandler.getResult();
        System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+ result.toString());
        for (ResultatItem res : result.getResultatItems()) {

            System.out.println("Id " + res.getIdr());
            System.out.println("sddddddddd " + res.toString());
            System.out.println("Date " + res.getDate());

            System.out.println("Title " + res.getTitre());
            System.out.println("Description " + res.getContenu());
            System.out.println("URL " + res.getUrl());

            // ResultatItem ResultatItem = new ResultatItem();
          /*  res.setContenu(res.getContenu());
            res.setTitre(res.getTitre());
            res.setUrl(res.getUrl());*/




            //resultatItemRepository.save(res);
        }
        /*for (NotificationHandler notificationHandler : notifications) {
            //notificationHandler.send(result);
        }*/
        }


    }

