package com.mycompany.myapp.web.rest;


import com.mycompany.myapp.domain.Recherche;
import com.mycompany.myapp.repository.RechercheRepository;
import com.mycompany.myapp.scrappingDeamon.WatchDog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.Executor;

@RestController
@RequestMapping("/api")
public class ScrapingResource {

    @Autowired
    private static RechercheRepository rechercheRepository;

    private  WatchDog watchDog;

    @Value("${scheduler.pool.size:10}")
    private Integer poolSize;

    public ScrapingResource(WatchDog watchDog, RechercheRepository rechercheRepository) {

        this.rechercheRepository = rechercheRepository;
        this.watchDog = watchDog;

    }
    @GetMapping("scrapping")
    public void scraping(){
        List<Recherche> recherches = rechercheRepository.findAllWithEagerRelationships();
        watchDog.init(recherches);
        watchDog.run();
    }
    @GetMapping("stop")
    public Executor taskExecutor() {
        // use the Spring ThreadPoolTaskScheduler
        ThreadPoolTaskScheduler scheduledExecutorService = new ThreadPoolTaskScheduler();
        // always set the poolsize
        scheduledExecutorService.setPoolSize(10);
        // for logging add a threadNamePrefix
        scheduledExecutorService.setThreadNamePrefix("myTaskScheduler-");
        // do not wait for completion of the task
        scheduledExecutorService.setWaitForTasksToCompleteOnShutdown(false);

        return scheduledExecutorService;
    }


}
