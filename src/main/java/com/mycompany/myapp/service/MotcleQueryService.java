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

import io.github.jhipster.service.QueryService;

import com.mycompany.myapp.domain.Motcle;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.MotcleRepository;
import com.mycompany.myapp.service.dto.MotcleCriteria;

/**
 * Service for executing complex queries for Motcle entities in the database.
 * The main input is a {@link MotcleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Motcle} or a {@link Page} of {@link Motcle} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MotcleQueryService extends QueryService<Motcle> {

    private final Logger log = LoggerFactory.getLogger(MotcleQueryService.class);

    private final MotcleRepository motcleRepository;

    public MotcleQueryService(MotcleRepository motcleRepository) {
        this.motcleRepository = motcleRepository;
    }

    /**
     * Return a {@link List} of {@link Motcle} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Motcle> findByCriteria(MotcleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Motcle> specification = createSpecification(criteria);
        return motcleRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Motcle} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Motcle> findByCriteria(MotcleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Motcle> specification = createSpecification(criteria);
        return motcleRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MotcleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Motcle> specification = createSpecification(criteria);
        return motcleRepository.count(specification);
    }

    /**
     * Function to convert MotcleCriteria to a {@link Specification}
     */
    private Specification<Motcle> createSpecification(MotcleCriteria criteria) {
        Specification<Motcle> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Motcle_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Motcle_.nom));
            }
            if (criteria.getMotinclue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMotinclue(), Motcle_.motinclue));
            }
            if (criteria.getMotexclue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMotexclue(), Motcle_.motexclue));
            }
            if (criteria.getRechercheId() != null) {
                specification = specification.and(buildSpecification(criteria.getRechercheId(),
                    root -> root.join(Motcle_.recherches, JoinType.LEFT).get(Recherche_.id)));
            }
        }
        return specification;
    }
}
