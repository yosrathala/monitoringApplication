package com.mycompany.myapp.web.rest;


import com.mycompany.myapp.domain.Recherche;
import com.mycompany.myapp.domain.ResultatItem;
import com.mycompany.myapp.domain.ResultatRecherche;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.RechercheRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.scrappingDeamon.WatchDog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ScrapingResource {

    @Autowired
    private static RechercheRepository rechercheRepository;


    private WatchDog watchDog;

    @Value("${scheduler.pool.size:10}")
    private Integer poolSize;

    public ScrapingResource(WatchDog watchDog, RechercheRepository rechercheRepository) {

        this.rechercheRepository = rechercheRepository;
        this.watchDog = watchDog;

    }

    @GetMapping("scrapping")
    public void scraping() {
        if (!watchDog.isRuning()) {

            List<Recherche> recherches = rechercheRepository.findAllWithEagerRelationships();
            watchDog.init(recherches);
            watchDog.run();


        }

    }

    @GetMapping("stop")
    public void taskExecutor() {
        watchDog.stopAll();
    }


}
