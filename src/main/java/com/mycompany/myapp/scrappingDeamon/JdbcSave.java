package com.mycompany.myapp.scrappingDeamon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.ResultatItem;
import com.mycompany.myapp.domain.ResultatRecherche;
import com.mycompany.myapp.repository.ResultatItemRepository;
import com.mycompany.myapp.repository.ResultatRechercheRepository;

@Service
@Transactional
public class JdbcSave extends SearchRresultHandler {
    public JdbcSave() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Autowired
    JavaMailSender emailSender;
    @Autowired
    UserRepository userRepository;
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

    @Override
    public void save(ResultatRecherche resultatRecherche) {
        List<ResultatItem> newItems = new ArrayList<>();
        for (ResultatItem res : resultatRecherche.getResultatItems()) {
            Optional<ResultatItem> ri = resultatItemRepository.findByPostId(res.getPostId());
            if (!ri.isPresent()) {
                newItems.add(res);
            }
        }

        if (newItems.size() > 0) {
            resultRechercheRepository.save(resultatRecherche);

            for (ResultatItem res : newItems) {
                res.setResultatRecherche(resultatRecherche);


                if (resultatRecherche.getRecherche().isEmailnotif()) {
                    SimpleMailMessage message = new SimpleMailMessage();
                    List<User> lstuser = userRepository.findAll();
                    for (int j = 0; j < lstuser.size(); j++) {


                        message.setTo(lstuser.get(j).getEmail());
                        message.setSubject("Résultat");
                        message.setText("**Titre**"+res.getTitre()+"\n**Contenu**"+res.getContenu()+"\n**url**"+res.getUrl());
                        this.emailSender.send(message);

                    }
                }


                resultatItemRepository.save(res);
            }
        }

    }

    public ResultatItem saveitem(ResultatItem resultatItem) {
        // TODO Auto-generated method stub
        return resultatItemRepository.save(resultatItem);
    }

}
