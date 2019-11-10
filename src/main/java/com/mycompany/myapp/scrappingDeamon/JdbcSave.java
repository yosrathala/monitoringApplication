package com.mycompany.myapp.scrappingDeamon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.Recherche;
import com.mycompany.myapp.domain.ResultatItem;
import com.mycompany.myapp.domain.ResultatRecherche;
import com.mycompany.myapp.domain.Source;
import com.mycompany.myapp.repository.RechercheRepository;
import com.mycompany.myapp.repository.ResultatItemRepository;
import com.mycompany.myapp.repository.ResultatRechercheRepository;
import com.mycompany.myapp.repository.SourceRepository;
import com.mycompany.myapp.spark.SVMPostInterest;

@Service
@Transactional
public class JdbcSave extends SearchResultHandler {
    public JdbcSave() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ResultatRechercheRepository getResultRechercheRepository() {
        return resultRechercheRepository;
    }

    public void setResultRechercheRepository(ResultatRechercheRepository resultRechercheRepository) {
        this.resultRechercheRepository = resultRechercheRepository;
    }

    public ResultatItemRepository getResultatItemRepository() {
        return resultatItemRepository;
    }

    public void setResultatItemRepository(ResultatItemRepository resultatItemRepository) {
        this.resultatItemRepository = resultatItemRepository;
    }

    @Autowired
    private ResultatRechercheRepository resultRechercheRepository;
    @Autowired
    private ResultatItemRepository resultatItemRepository;
    
    @Autowired
    private RechercheRepository rechercheRepository;
    
    @Autowired
    private SourceRepository sourceRepository;

    @Override
    public List<ResultatItem> save(ResultatRecherche resultatRecherche, JobConfig jobConfig) {
    	Optional<Recherche> search = rechercheRepository.findById(jobConfig.getSearchId());
    	Optional<Source> source = sourceRepository.findById(jobConfig.getSourceId());
    	
        List<ResultatItem> newItems = new ArrayList<>();
        resultatRecherche.setRecherche(search.get());
        resultatRecherche.setSource(source.get());
        
        for (ResultatItem res : resultatRecherche.getResultatItems()) {
            Optional<ResultatItem> ri = resultatItemRepository.findByPostId(res.getPostId());
            if (!ri.isPresent()) {
                newItems.add(res);
              
            }
        }
        if (newItems.size() > 0) {
            resultRechercheRepository.save(resultatRecherche);

            for (ResultatItem newResult : newItems) {
            	newResult.setResultatRecherche(resultatRecherche);
            	

                // appel api sur newResult newResult.getContenu()
                
            	boolean result = false;
            	result = SVMPostInterest.predict(newResult.getContenu());	
            	System.err.println("-----------------------Post Text ------------------------------");
            	System.err.println(newResult.getContenu());
            	System.err.println("-------------------------------- ------------------------------");
							
				System.err.println("======================> PREDICTION result : " + result);
				if(result==true) {           		          		
            		resultatItemRepository.save(newResult);         		
            	}
            	SVMPostInterest.getSc().stop();
            	SVMPostInterest.getSc().close();
            }
        }
       
        return newItems;

    }

}
