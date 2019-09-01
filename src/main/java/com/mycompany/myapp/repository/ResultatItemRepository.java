package com.mycompany.myapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycompany.myapp.domain.ResultatItem;


/**
 * Spring Data  repository for the ResultatItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultatItemRepository extends JpaRepository<ResultatItem, Long> {
    Optional<ResultatItem> findByPostId(String postId);
}
