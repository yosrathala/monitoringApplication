package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Motcle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Motcle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MotcleRepository extends JpaRepository<Motcle, Long>, JpaSpecificationExecutor<Motcle> {

}
