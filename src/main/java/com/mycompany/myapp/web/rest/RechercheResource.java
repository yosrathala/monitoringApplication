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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.myapp.domain.Recherche;
import com.mycompany.myapp.repository.RechercheRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Recherche.
 */
@RestController
@RequestMapping("/api")
public class RechercheResource {

    private final Logger log = LoggerFactory.getLogger(RechercheResource.class);

    private static final String ENTITY_NAME = "recherche";

    private final RechercheRepository rechercheRepository;

    public RechercheResource(RechercheRepository rechercheRepository) {
        this.rechercheRepository = rechercheRepository;
    }

    /**
     * POST  /recherches : Create a new recherche.
     *
     * @param recherche the recherche to create
     * @return the ResponseEntity with status 201 (Created) and with body the new recherche, or with status 400 (Bad Request) if the recherche has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/recherches")
    public ResponseEntity<Recherche> createRecherche(@Valid @RequestBody Recherche recherche) throws URISyntaxException {
        log.debug("REST request to save Recherche : {}", recherche);
        if (recherche.getId() != null) {
            throw new BadRequestAlertException("A new recherche cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Recherche result = rechercheRepository.save(recherche);
        return ResponseEntity.created(new URI("/api/recherches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /recherches : Updates an existing recherche.
     *
     * @param recherche the recherche to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated recherche,
     * or with status 400 (Bad Request) if the recherche is not valid,
     * or with status 500 (Internal Server Error) if the recherche couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/recherches")
    public ResponseEntity<Recherche> updateRecherche(@Valid @RequestBody Recherche recherche) throws URISyntaxException {
        log.debug("REST request to update Recherche : {}", recherche);
        if (recherche.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Recherche result = rechercheRepository.save(recherche);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, recherche.getId().toString()))
            .body(result);
    }

    /**
     * GET  /recherches : get all the recherches.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of recherches in body
     */
    @GetMapping("/recherches")
    public ResponseEntity<List<Recherche>> getAllRecherches(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Recherches");
        Page<Recherche> page;
        if (eagerload) {
            page = rechercheRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = rechercheRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/recherches?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /recherches/:id : get the "id" recherche.
     *
     * @param id the id of the recherche to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the recherche, or with status 404 (Not Found)
     */
    @GetMapping("/recherches/{id}")
    public ResponseEntity<Recherche> getRecherche(@PathVariable Long id) {
        log.debug("REST request to get Recherche : {}", id);
        Optional<Recherche> recherche = rechercheRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(recherche);
    }

    /**
     * DELETE  /recherches/:id : delete the "id" recherche.
     *
     * @param id the id of the recherche to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/recherches/{id}")
    public ResponseEntity<Void> deleteRecherche(@PathVariable Long id) {
        log.debug("REST request to delete Recherche : {}", id);
        rechercheRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
