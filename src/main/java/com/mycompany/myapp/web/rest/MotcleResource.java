package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.Motcle;
import com.mycompany.myapp.repository.MotcleRepository;
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
 * REST controller for managing Motcle.
 */
@RestController
@RequestMapping("/api")
public class MotcleResource {

    private final Logger log = LoggerFactory.getLogger(MotcleResource.class);

    private static final String ENTITY_NAME = "motcle";

    private final MotcleRepository motcleRepository;

    public MotcleResource(MotcleRepository motcleRepository) {
        this.motcleRepository = motcleRepository;
    }

    /**
     * POST  /motcles : Create a new motcle.
     *
     * @param motcle the motcle to create
     * @return the ResponseEntity with status 201 (Created) and with body the new motcle, or with status 400 (Bad Request) if the motcle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/motcles")
    public ResponseEntity<Motcle> createMotcle(@Valid @RequestBody Motcle motcle) throws URISyntaxException {
        log.debug("REST request to save Motcle : {}", motcle);
        if (motcle.getId() != null) {
            throw new BadRequestAlertException("A new motcle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Motcle result = motcleRepository.save(motcle);
        return ResponseEntity.created(new URI("/api/motcles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /motcles : Updates an existing motcle.
     *
     * @param motcle the motcle to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated motcle,
     * or with status 400 (Bad Request) if the motcle is not valid,
     * or with status 500 (Internal Server Error) if the motcle couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/motcles")
    public ResponseEntity<Motcle> updateMotcle(@Valid @RequestBody Motcle motcle) throws URISyntaxException {
        log.debug("REST request to update Motcle : {}", motcle);
        if (motcle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Motcle result = motcleRepository.save(motcle);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, motcle.getId().toString()))
            .body(result);
    }

    /**
     * GET  /motcles : get all the motcles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of motcles in body
     */
    @GetMapping("/motcles")
    public ResponseEntity<List<Motcle>> getAllMotcles(Pageable pageable) {
        log.debug("REST request to get a page of Motcles");
        Page<Motcle> page = motcleRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/motcles");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /motcles/:id : get the "id" motcle.
     *
     * @param id the id of the motcle to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the motcle, or with status 404 (Not Found)
     */
    @GetMapping("/motcles/{id}")
    public ResponseEntity<Motcle> getMotcle(@PathVariable Long id) {
        log.debug("REST request to get Motcle : {}", id);
        Optional<Motcle> motcle = motcleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(motcle);
    }

    /**
     * DELETE  /motcles/:id : delete the "id" motcle.
     *
     * @param id the id of the motcle to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/motcles/{id}")
    public ResponseEntity<Void> deleteMotcle(@PathVariable Long id) {
        log.debug("REST request to delete Motcle : {}", id);
        motcleRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
