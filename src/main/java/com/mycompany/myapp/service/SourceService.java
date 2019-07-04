package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Source;
import com.mycompany.myapp.repository.SourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Source.
 */
@Service
@Transactional
public class SourceService {

    private final Logger log = LoggerFactory.getLogger(SourceService.class);

    private final SourceRepository sourceRepository;

    public SourceService(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
    }

    /**
     * Save a source.
     *
     * @param source the entity to save
     * @return the persisted entity
     */
    public Source save(Source source) {
        log.debug("Request to save Source : {}", source);
        return sourceRepository.save(source);
    }

    /**
     * Get all the sources.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Source> findAll(Pageable pageable) {
        log.debug("Request to get all Sources");
        return sourceRepository.findAll(pageable);
    }


    /**
     * Get one source by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Source> findOne(Long id) {
        log.debug("Request to get Source : {}", id);
        return sourceRepository.findById(id);
    }

    /**
     * Delete the source by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Source : {}", id);
        sourceRepository.deleteById(id);
    }
}
