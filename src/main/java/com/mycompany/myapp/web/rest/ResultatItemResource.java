package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.ResultatItem;
import com.mycompany.myapp.repository.ResultatItemRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ResultatItem.
 */
@RestController
@RequestMapping("/api")
public class ResultatItemResource {

    private final Logger log = LoggerFactory.getLogger(ResultatItemResource.class);

    private static final String ENTITY_NAME = "resultatItem";

    private final ResultatItemRepository resultatItemRepository;

    public ResultatItemResource(ResultatItemRepository resultatItemRepository) {
        this.resultatItemRepository = resultatItemRepository;
    }

    /**
     * POST  /resultat-items : Create a new resultatItem.
     *
     * @param resultatItem the resultatItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resultatItem, or with status 400 (Bad Request) if the resultatItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/resultat-items")
    public ResponseEntity<ResultatItem> createResultatItem(@Valid @RequestBody ResultatItem resultatItem) throws URISyntaxException {
        log.debug("REST request to save ResultatItem : {}", resultatItem);
        if (resultatItem.getId() != null) {
            throw new BadRequestAlertException("A new resultatItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResultatItem result = resultatItemRepository.save(resultatItem);
        return ResponseEntity.created(new URI("/api/resultat-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /resultat-items : Updates an existing resultatItem.
     *
     * @param resultatItem the resultatItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resultatItem,
     * or with status 400 (Bad Request) if the resultatItem is not valid,
     * or with status 500 (Internal Server Error) if the resultatItem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/resultat-items")
    public ResponseEntity<ResultatItem> updateResultatItem(@Valid @RequestBody ResultatItem resultatItem) throws URISyntaxException {
        log.debug("REST request to update ResultatItem : {}", resultatItem);
        if (resultatItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResultatItem result = resultatItemRepository.save(resultatItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, resultatItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /resultat-items : get all the resultatItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of resultatItems in body
     */
    @GetMapping("/resultat-items")
    public ResponseEntity<List<ResultatItem>> getAllResultatItems(Pageable pageable) {
        log.debug("REST request to get a page of ResultatItems");
        Page<ResultatItem> page = resultatItemRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/resultat-items");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /resultat-items/:id : get the "id" resultatItem.
     *
     * @param id the id of the resultatItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resultatItem, or with status 404 (Not Found)
     */
    @GetMapping("/resultat-items/{id}")
    public ResponseEntity<ResultatItem> getResultatItem(@PathVariable Long id) {
        log.debug("REST request to get ResultatItem : {}", id);
        Optional<ResultatItem> resultatItem = resultatItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(resultatItem);
    }

    /**
     * DELETE  /resultat-items/:id : delete the "id" resultatItem.
     *
     * @param id the id of the resultatItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/resultat-items/{id}")
    public ResponseEntity<Void> deleteResultatItem(@PathVariable Long id) {
        log.debug("REST request to delete ResultatItem : {}", id);
        resultatItemRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
