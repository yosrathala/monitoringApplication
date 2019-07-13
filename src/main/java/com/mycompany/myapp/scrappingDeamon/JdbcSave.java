package com.mycompany.myapp.scrappingDeamon;

import javax.transaction.Transactional;

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

    @Override
    public ResultatRecherche save(ResultatRecherche resultatRecherche) {
        // TODO Auto-generated method stub
        return resultRechercheRepository.save(resultatRecherche);
    }


}
