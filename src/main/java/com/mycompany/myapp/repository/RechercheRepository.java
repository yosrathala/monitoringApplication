package com.mycompany.myapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mycompany.myapp.domain.Recherche;

/**
 * Spring Data  repository for the Recherche entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RechercheRepository extends JpaRepository<Recherche, Long> {

    @Query(value = "select distinct recherche from Recherche recherche left join fetch recherche.sources",
        countQuery = "select count(distinct recherche) from Recherche recherche")
    Page<Recherche> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct recherche from Recherche recherche left join fetch recherche.sources")
    List<Recherche> findAllWithEagerRelationships();

    @Query("select recherche from Recherche recherche left join fetch recherche.sources where recherche.id =:id")
    Optional<Recherche> findOneWithEagerRelationships(@Param("id") Long id);

}
