package com.mycompany.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycompany.myapp.domain.ResultatRecherche;


/**
 * Spring Data  repository for the ResultatRecherche entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultatRechercheRepository extends JpaRepository<ResultatRecherche, Long> {

}
