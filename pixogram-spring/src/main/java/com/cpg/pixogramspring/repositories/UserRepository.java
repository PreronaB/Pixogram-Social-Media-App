package com.cpg.pixogramspring.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cpg.pixogramspring.models.Role;
import com.cpg.pixogramspring.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	public User findByEmail(String email);
	
	@Query("select u from User u where u.user_id=?1")
	Optional<User> findByUserId(int user_id);
	
	User findByEmailAndPassword(String email, String password);
	
	@Query(value="select * from User where user_id=:user_id and email=:email", nativeQuery = true)
	Optional<User> findByUserIdAndEmail(int user_id, String email);
	
	@Query("FROM Role r where r.rolename=?1")
	Role findRole(String rolename);

}
