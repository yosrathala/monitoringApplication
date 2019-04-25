package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ResultatRecherche;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ResultatRecherche entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultatRechercheRepository extends JpaRepository<ResultatRecherche, Long> {

}
