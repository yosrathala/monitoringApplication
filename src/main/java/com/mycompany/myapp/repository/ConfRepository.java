package com.mycompany.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycompany.myapp.domain.Conf;


/**
 * Spring Data  repository for the Conf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfRepository extends JpaRepository<Conf, Long> {

}
