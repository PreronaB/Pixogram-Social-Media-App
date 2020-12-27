package com.cpg.pixogramspring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cpg.pixogramspring.models.Content;

public interface ContentRepository extends JpaRepository<Content, Integer>  {
	
	Content findByCaption(String caption);

	@Query("select c from Content c where c.content_id=?1")
	Content findContentById(int content_id);
}
