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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.myapp.domain.Source;
import com.mycompany.myapp.service.SourceQueryService;
import com.mycompany.myapp.service.SourceService;
import com.mycompany.myapp.service.dto.SourceCriteria;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Source.
 */
@RestController
@RequestMapping("/api")

@PreAuthorize("hasRole('ADMIN')or hasRole('USER')")
public class SourceResource {

    private final Logger log = LoggerFactory.getLogger(SourceResource.class);

    private static final String ENTITY_NAME = "source";

    private final SourceService sourceService;

    private final SourceQueryService sourceQueryService;

    public SourceResource(SourceService sourceService, SourceQueryService sourceQueryService) {
        this.sourceService = sourceService;
        this.sourceQueryService = sourceQueryService;
    }

    /**
     * POST  /sources : Create a new source.
     *
     * @param source the source to create
     * @return the ResponseEntity with status 201 (Created) and with body the new source, or with status 400 (Bad Request) if the source has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sources")
    public ResponseEntity<Source> createSource(@Valid @RequestBody Source source) throws URISyntaxException {
        log.debug("REST request to save Source : {}", source);
        if (source.getId() != null) {
            throw new BadRequestAlertException("A new source cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Source result = sourceService.save(source);
        return ResponseEntity.created(new URI("/api/sources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sources : Updates an existing source.
     *
     * @param source the source to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated source,
     * or with status 400 (Bad Request) if the source is not valid,
     * or with status 500 (Internal Server Error) if the source couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sources")
    public ResponseEntity<Source> updateSource(@Valid @RequestBody Source source) throws URISyntaxException {
        log.debug("REST request to update Source : {}", source);
        if (source.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Source result = sourceService.save(source);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, source.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sources : get all the sources.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of sources in body
     */
    @GetMapping("/sources")
    public ResponseEntity<List<Source>> getAllSources(SourceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Sources by criteria: {}", criteria);
        Page<Source> page = sourceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sources");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /sources/count : count all the sources.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/sources/count")
    public ResponseEntity<Long> countSources(SourceCriteria criteria) {
        log.debug("REST request to count Sources by criteria: {}", criteria);
        return ResponseEntity.ok().body(sourceQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /sources/:id : get the "id" source.
     *
     * @param id the id of the source to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the source, or with status 404 (Not Found)
     */
    @GetMapping("/sources/{id}")
    public ResponseEntity<Source> getSource(@PathVariable Long id) {
        log.debug("REST request to get Source : {}", id);
        Optional<Source> source = sourceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(source);
    }

    /**
     * DELETE  /sources/:id : delete the "id" source.
     *
     * @param id the id of the source to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sources/{id}")
    public ResponseEntity<Void> deleteSource(@PathVariable Long id) {
        log.debug("REST request to delete Source : {}", id);
        sourceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
