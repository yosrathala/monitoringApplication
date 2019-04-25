package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Conf;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Conf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfRepository extends JpaRepository<Conf, Long> {

}
