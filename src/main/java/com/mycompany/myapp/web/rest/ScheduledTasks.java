package com.mycompany.myapp.web.rest;


import java.time.ZonedDateTime;
import java.util.*;

import com.mycompany.myapp.domain.Recherche;
import com.mycompany.myapp.domain.Source;
import com.mycompany.myapp.repository.RechercheRepository;
import com.mycompany.myapp.scrappingDeamon.ProgolinScrapingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@Component
public class ScheduledTasks {
	List<Recherche> l=new ArrayList<Recherche>();
		@Autowired
		private RechercheRepository rechercheRepository;

		@Autowired
		private ProgolinScrapingHandler progolinScrapingHandler;
		@Autowired
		private JdbcTemplate jdbcTemplate;
		
		@Scheduled(fixedRate = 800000)
	    public void reportCurrentTime() {
	    	/*l =rechercheRepository.findAll();


	    	if(l!=null) {
	    for(int i=0;i<l.size();i++) {
	        Recherche rr =l.get(i);
            List sources=  jdbcTemplate.queryForList("select * FROM source s,recherche rech, recherche_source r WHERE s.id= r.source_id AND r.recherche_id=rech.id  ");
           for(Object j :sources){*/
	    	   	     progolinScrapingHandler.login("yosraa","yosra1234yosra","https://www.progonline.com/");
                // }
	    	   	 for (int x=0;x<progolinScrapingHandler.projectList.size();x++){
                     Date date=new Date(progolinScrapingHandler.projectList.get(x).getDate());
                     Recherche r =rechercheRepository.findById(Long.parseLong("1701")).get();
                     Long id=r.getId();
                     jdbcTemplate.update("INSERT INTO resultat_recherche(id,jhi_date,recherche_id) VALUES(?,?,?)",1,new Date(),1701);
                 }

		    	 

	  //  }

	    //}
	    }
}
