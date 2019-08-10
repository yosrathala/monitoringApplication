package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ResultatItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the ResultatItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultatItemRepository extends JpaRepository<ResultatItem, Long> {
    Optional<ResultatItem> findByPostId(String postId);
}
