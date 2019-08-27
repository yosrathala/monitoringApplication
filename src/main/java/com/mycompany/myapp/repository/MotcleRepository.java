package com.mycompany.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.mycompany.myapp.domain.Motcle;


/**
 * Spring Data  repository for the Motcle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MotcleRepository extends JpaRepository<Motcle, Long>, JpaSpecificationExecutor<Motcle> {

}
