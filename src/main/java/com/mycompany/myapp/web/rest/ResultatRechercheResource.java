package com.mycompany.myapp.web.rest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.myapp.domain.ResultatRecherche;
import com.mycompany.myapp.repository.ResultatRechercheRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing ResultatRecherche.
 */
@RestController
@RequestMapping("/api")
public class
ResultatRechercheResource {

    private final Logger log = LoggerFactory.getLogger(ResultatRechercheResource.class);

    private static final String ENTITY_NAME = "resultatRecherche";

    private final ResultatRechercheRepository resultatRechercheRepository;

    public ResultatRechercheResource(ResultatRechercheRepository resultatRechercheRepository) {
        this.resultatRechercheRepository = resultatRechercheRepository;
    }

    /**
     * POST  /resultat-recherches : Create a new resultatRecherche.
     *
     * @param resultatRecherche the resultatRecherche to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resultatRecherche, or with status 400 (Bad Request) if the resultatRecherche has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/resultat-recherches")
    public ResponseEntity<ResultatRecherche> createResultatRecherche(@Valid @RequestBody ResultatRecherche resultatRecherche) throws URISyntaxException {
        log.debug("REST request to save ResultatRecherche : {}", resultatRecherche);
        if (resultatRecherche.getId() != null) {
            throw new BadRequestAlertException("A new resultatRecherche cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResultatRecherche result = resultatRechercheRepository.save(resultatRecherche);
        return ResponseEntity.created(new URI("/api/resultat-recherches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /resultat-recherches : Updates an existing resultatRecherche.
     *
     * @param resultatRecherche the resultatRecherche to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resultatRecherche,
     * or with status 400 (Bad Request) if the resultatRecherche is not valid,
     * or with status 500 (Internal Server Error) if the resultatRecherche couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/resultat-recherches")
    public ResponseEntity<ResultatRecherche> updateResultatRecherche(@Valid @RequestBody ResultatRecherche resultatRecherche) throws URISyntaxException {
        log.debug("REST request to update ResultatRecherche : {}", resultatRecherche);
        if (resultatRecherche.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResultatRecherche result = resultatRechercheRepository.save(resultatRecherche);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, resultatRecherche.getId().toString()))
            .body(result);
    }

    /**
     * GET  /resultat-recherches : get all the resultatRecherches.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of resultatRecherches in body
     */
    @GetMapping("/resultat-recherches")
    public ResponseEntity<List<ResultatRecherche>> getAllResultatRecherches(Pageable pageable) {
        log.debug("REST request to get a page of ResultatRecherches");
        Page<ResultatRecherche> page = resultatRechercheRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/resultat-recherches");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /resultat-recherches/:id : get the "id" resultatRecherche.
     *
     * @param id the id of the resultatRecherche to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resultatRecherche, or with status 404 (Not Found)
     */
    @GetMapping("/resultat-recherches/{id}")
    public ResponseEntity<ResultatRecherche> getResultatRecherche(@PathVariable Long id) {
        log.debug("REST request to get ResultatRecherche : {}", id);
        Optional<ResultatRecherche> resultatRecherche = resultatRechercheRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(resultatRecherche);
    }

    /**
     * DELETE  /resultat-recherches/:id : delete the "id" resultatRecherche.
     *
     * @param id the id of the resultatRecherche to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/resultat-recherches/{id}")
    public ResponseEntity<Void> deleteResultatRecherche(@PathVariable Long id) {
        log.debug("REST request to delete ResultatRecherche : {}", id);
        resultatRechercheRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
