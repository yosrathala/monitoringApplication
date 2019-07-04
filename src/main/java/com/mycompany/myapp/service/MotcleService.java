package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Motcle;
import com.mycompany.myapp.repository.MotcleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Motcle.
 */
@Service
@Transactional
public class MotcleService {

    private final Logger log = LoggerFactory.getLogger(MotcleService.class);

    private final MotcleRepository motcleRepository;

    public MotcleService(MotcleRepository motcleRepository) {
        this.motcleRepository = motcleRepository;
    }

    /**
     * Save a motcle.
     *
     * @param motcle the entity to save
     * @return the persisted entity
     */
    public Motcle save(Motcle motcle) {
        log.debug("Request to save Motcle : {}", motcle);
        return motcleRepository.save(motcle);
    }

    /**
     * Get all the motcles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Motcle> findAll(Pageable pageable) {
        log.debug("Request to get all Motcles");
        return motcleRepository.findAll(pageable);
    }


    /**
     * Get one motcle by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Motcle> findOne(Long id) {
        log.debug("Request to get Motcle : {}", id);
        return motcleRepository.findById(id);
    }

    /**
     * Delete the motcle by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Motcle : {}", id);
        motcleRepository.deleteById(id);
    }
}
