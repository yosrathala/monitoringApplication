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

import com.mycompany.myapp.domain.Conf;
import com.mycompany.myapp.repository.ConfRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Conf.
 */
@RestController
@RequestMapping("/api")
public class ConfResource {

    private final Logger log = LoggerFactory.getLogger(ConfResource.class);

    private static final String ENTITY_NAME = "conf";

    private final ConfRepository confRepository;

    public ConfResource(ConfRepository confRepository) {
        this.confRepository = confRepository;
    }

    /**
     * POST  /confs : Create a new conf.
     *
     * @param conf the conf to create
     * @return the ResponseEntity with status 201 (Created) and with body the new conf, or with status 400 (Bad Request) if the conf has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/confs")
    public ResponseEntity<Conf> createConf(@Valid @RequestBody Conf conf) throws URISyntaxException {
        log.debug("REST request to save Conf : {}", conf);
        if (conf.getId() != null) {
            throw new BadRequestAlertException("A new conf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Conf result = confRepository.save(conf);
        return ResponseEntity.created(new URI("/api/confs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /confs : Updates an existing conf.
     *
     * @param conf the conf to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated conf,
     * or with status 400 (Bad Request) if the conf is not valid,
     * or with status 500 (Internal Server Error) if the conf couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/confs")
    public ResponseEntity<Conf> updateConf(@Valid @RequestBody Conf conf) throws URISyntaxException {
        log.debug("REST request to update Conf : {}", conf);
        if (conf.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Conf result = confRepository.save(conf);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, conf.getId().toString()))
            .body(result);
    }

    /**
     * GET  /confs : get all the confs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of confs in body
     */
    @GetMapping("/confs")
    public ResponseEntity<List<Conf>> getAllConfs(Pageable pageable) {
        log.debug("REST request to get a page of Confs");
        Page<Conf> page = confRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/confs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /confs/:id : get the "id" conf.
     *
     * @param id the id of the conf to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the conf, or with status 404 (Not Found)
     */
    @GetMapping("/confs/{id}")
    public ResponseEntity<Conf> getConf(@PathVariable Long id) {
        log.debug("REST request to get Conf : {}", id);
        Optional<Conf> conf = confRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(conf);
    }

    /**
     * DELETE  /confs/:id : delete the "id" conf.
     *
     * @param id the id of the conf to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/confs/{id}")
    public ResponseEntity<Void> deleteConf(@PathVariable Long id) {
        log.debug("REST request to delete Conf : {}", id);
        confRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
