package com.mycompany.myapp.web.rest;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.myapp.domain.Recherche;
import com.mycompany.myapp.repository.RechercheRepository;
import com.mycompany.myapp.scrappingDeamon.WatchDog;

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
