package com.mycompany.myapp.scrappingDeamon;

import javax.transaction.Transactional;

import com.mycompany.myapp.domain.ResultatItem;
import com.mycompany.myapp.repository.ResultatItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.mycompany.myapp.domain.ResultatRecherche;
import com.mycompany.myapp.repository.ResultatRechercheRepository;
@Transactional
public class JdbcSave extends SearchRresultHandler{
    public JdbcSave() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Autowired
    private ResultatRechercheRepository resultRechercheRepository;
    @Autowired
    private ResultatItemRepository resultatItemRepository;

    @Override
    public ResultatRecherche save(ResultatRecherche resultatRecherche) {
        // TODO Auto-generated method stub
        return resultRechercheRepository.save(resultatRecherche);
    }

    public ResultatItem saveitem(ResultatItem resultatItem) {
        // TODO Auto-generated method stub
        return resultatItemRepository.save(resultatItem);
    }


}
