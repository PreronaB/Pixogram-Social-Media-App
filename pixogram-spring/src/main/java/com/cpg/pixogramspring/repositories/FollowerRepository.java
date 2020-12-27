package com.cpg.pixogramspring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cpg.pixogramspring.models.Followers;

public interface FollowerRepository extends JpaRepository<Followers, Integer> {
	
	@Query("select f from Followers f where f.follower__email=?1")
	Optional<Followers> findByEmail(String follower_email);
	
	@Query(value="select * from followers where follower_id=:followerId and user_id=:userId", nativeQuery=true)
	Optional<Followers> findByFollowerIdAndUserId(int followerId, int userId);
	
	@Query(value = "select * from followers where user_id=:user_id", nativeQuery = true)
	List<Followers> findByFollowerId(int user_id);

}
