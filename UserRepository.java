package com.cpg.pixogramspring.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cpg.pixogramspring.entities.Role;
import com.cpg.pixogramspring.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByEmail(String email);
	
	public User findByEmailAndPassword(String email, String password);
	
	public Optional<User> findById(Integer user_id);
	
	@Query("SELECT count(u) FROM User u")
	Long countUsers();
	
	@Query("FROM Role r where r.rolename=?1")
	public Role findRole(String rolename);
	
}
