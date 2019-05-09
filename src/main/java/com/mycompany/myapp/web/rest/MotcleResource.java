package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.Motcle;
import com.mycompany.myapp.service.MotcleService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.service.dto.MotcleCriteria;
import com.mycompany.myapp.service.MotcleQueryService;
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

    private final MotcleService motcleService;

    private final MotcleQueryService motcleQueryService;

    public MotcleResource(MotcleService motcleService, MotcleQueryService motcleQueryService) {
        this.motcleService = motcleService;
        this.motcleQueryService = motcleQueryService;
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
        Motcle result = motcleService.save(motcle);
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
        Motcle result = motcleService.save(motcle);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, motcle.getId().toString()))
            .body(result);
    }

    /**
     * GET  /motcles : get all the motcles.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of motcles in body
     */
    @GetMapping("/motcles")
    public ResponseEntity<List<Motcle>> getAllMotcles(MotcleCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Motcles by criteria: {}", criteria);
        Page<Motcle> page = motcleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/motcles");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /motcles/count : count all the motcles.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/motcles/count")
    public ResponseEntity<Long> countMotcles(MotcleCriteria criteria) {
        log.debug("REST request to count Motcles by criteria: {}", criteria);
        return ResponseEntity.ok().body(motcleQueryService.countByCriteria(criteria));
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
        Optional<Motcle> motcle = motcleService.findOne(id);
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
        motcleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
