package com.cpg.pixogramspring.services;

import java.util.List;
import com.cpg.pixogramspring.entities.User;
import com.cpg.pixogramspring.exceptions.ValidationException;

public interface UserService {
	
	public User addUser(User user) throws ValidationException;
	
	public void deleteUser(int user_id); 
	
	public List<User> getAllUsers();
	
	public User loginUser(String email, String password);
	
	public User getUserById(int user_id);
	
	public User getUserByEmail(String email);
	
	public User updateUser(User user);

}
