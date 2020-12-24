package com.cpg.pixogramspring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cpg.pixogramspring.entities.Content;
import com.cpg.pixogramspring.entities.User;

public interface ContentRepository extends JpaRepository<Content, Integer>  {
	
	public Content findByCaption(String caption);
	
//	@Query("select u from User u where u.user_id=?1")
//	public User findUser(int user_id);

	@Query("select c from Content c where c.user=?1")
	public User findUserByContent(User user);
	
//	@Query("select c from Content c where c.user=?1 AND c.content_id=?2")
//	public User findUserByContent(User user, int content_id);

	@Query("select c from Content c where c.content_id=?1")
	public Content findContentById(int content_id);
	
//	@Query("select c from Content c where c.user=?1")
//	public Content findContentByUser(int user_id);

//	@Query("SELECT count(c.likes) FROM Content c")
//	 Long countLikes();
//	
//	@Query("SELECT count(c.unlikes) FROM Content c")
//	 Long countUnlikes();
	
}
