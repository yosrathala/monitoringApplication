package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Recherche;
import com.mycompany.myapp.domain.ResultatItem;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ResultatItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultatItemRepository extends JpaRepository<ResultatItem, Long> {
	
	
	 Optional<ResultatItem> findByPostId(String postId);
}
