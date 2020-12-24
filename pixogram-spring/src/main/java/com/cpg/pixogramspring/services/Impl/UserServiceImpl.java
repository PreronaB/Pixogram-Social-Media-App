package com.cpg.pixogramspring.services.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.cpg.pixogramspring.entities.Role;
import com.cpg.pixogramspring.entities.User;
import com.cpg.pixogramspring.exceptions.UserAlreadyExistsException;
import com.cpg.pixogramspring.exceptions.UserNotFoundException;
import com.cpg.pixogramspring.exceptions.ValidationException;
import com.cpg.pixogramspring.repositories.UserRepository;
import com.cpg.pixogramspring.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	
	
	@Value("User Already Exists!")
	private String userAlreadyExistsMessage;
	@Value("User not found!!")
	private String userNotFoundMessage;
	@Value("Use a correct Password(minimum length 8)")
	private String passwordValidation;
	@Value("Use a correct Email")
	private String emailValidation;
	
	@Autowired
	private UserRepository userRepository;
	
	public User addUser(User user) throws ValidationException {
		final String passwordpattern = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";
		final String emailpattern = "^(.+)@(.+)$";
		String pswrd = user.getPassword();
		String email = user.getEmail();
		if (email.matches(emailpattern)) {
			if (pswrd.matches(passwordpattern)) {
				User existingUser = userRepository.findByEmail(user.getEmail());
				if (existingUser != null) {
					throw new UserAlreadyExistsException(userAlreadyExistsMessage);
				}
				Role role = user.getRole();
				Role existingRole = userRepository.findRole(user.getRole().getRolename());
				if (existingRole.getRolename().equals(role.getRolename())) {
					user.setRole(existingRole);
				}	
				userRepository.save(user);
			}else {
				throw new ValidationException(passwordValidation);
			}
		} else {
			throw new ValidationException(emailValidation);
		}
		return user;
	}
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@Override
	public void deleteUser(int user_id)  {
		Optional<User> existingUser= userRepository.findById(user_id);
		if(!existingUser.isPresent()) {
			throw new UserNotFoundException(userNotFoundMessage);
		} else {
			userRepository.deleteById(user_id);
		}	
	}

	@Override
	public User loginUser(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
		
	}

	@Override
	public User getUserById(int user_id) {
		User user= userRepository.findUser(user_id);
		if(user!=null) {
			return user;
		}
		else {
			throw new UserNotFoundException(userNotFoundMessage);
		}
		
	}

	@Override
	public User getUserByEmail(String email) {
		User user= userRepository.findByEmail(email);
		if(user!=null) {
			return user;
		}
		else {
			throw new UserNotFoundException(userNotFoundMessage);
		}
	}

	@Override
	public User updateUser(User user) throws UserNotFoundException {
		User existingUser=userRepository.findUser(user.getUser_id());
		if(existingUser!=null) {
			Role role = user.getRole();
			Role existingRole = userRepository.findRole(user.getRole().getRolename());
			if (existingRole.getRolename().equals(role.getRolename())) {
				user.setRole(existingRole);
			}	
			return userRepository.save(user);
		}
		else {
			throw new UserNotFoundException(userNotFoundMessage);
		}
		
	}
		
}