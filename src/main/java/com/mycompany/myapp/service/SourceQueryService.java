package com.mycompany.myapp.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// for static metamodels
import com.mycompany.myapp.domain.Recherche_;
import com.mycompany.myapp.domain.Source;
import com.mycompany.myapp.domain.Source_;
import com.mycompany.myapp.repository.SourceRepository;
import com.mycompany.myapp.service.dto.SourceCriteria;

import io.github.jhipster.service.QueryService;

/**
 * Service for executing complex queries for Source entities in the database.
 * The main input is a {@link SourceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Source} or a {@link Page} of {@link Source} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SourceQueryService extends QueryService<Source> {

    private final Logger log = LoggerFactory.getLogger(SourceQueryService.class);

    private final SourceRepository sourceRepository;

    public SourceQueryService(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
    }

    /**
     * Return a {@link List} of {@link Source} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Source> findByCriteria(SourceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Source> specification = createSpecification(criteria);
        return sourceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Source} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Source> findByCriteria(SourceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Source> specification = createSpecification(criteria);
        return sourceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SourceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Source> specification = createSpecification(criteria);
        return sourceRepository.count(specification);
    }

    /**
     * Function to convert SourceCriteria to a {@link Specification}
     */
    private Specification<Source> createSpecification(SourceCriteria criteria) {
        Specification<Source> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Source_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Source_.nom));
            }
            if (criteria.getLogin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogin(), Source_.login));
            }
            if (criteria.getMotPasse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMotPasse(), Source_.motPasse));
            }
            if (criteria.getUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrl(), Source_.url));
            }
            if (criteria.getKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKey(), Source_.key));
            }
            if (criteria.getDataHandler() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDataHandler(), Source_.dataHandler));
            }
            if (criteria.getRechercheId() != null) {
                specification = specification.and(buildSpecification(criteria.getRechercheId(),
                    root -> root.join(Source_.recherches, JoinType.LEFT).get(Recherche_.id)));
            }
        }
        return specification;
    }
}
