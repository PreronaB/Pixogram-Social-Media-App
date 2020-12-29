package com.cpg.pixogramspring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cpg.pixogramspring.constants.UserConstants;
import com.cpg.pixogramspring.models.Content;
import com.cpg.pixogramspring.models.Followers;
import com.cpg.pixogramspring.models.User;
import com.cpg.pixogramspring.services.FollowerService;
import com.cpg.pixogramspring.services.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api")
@Api(value = "User", tags = { "UserAPI" })
public class UserController {

	@Autowired
	public FollowerService followerService;
	@Autowired
	private UserService userService;

	/**
	 * This method can be used by both admin and user Signing in as a user
	 * 
	 * @param user
	 * @return Response Status
	 */
	@GetMapping("/login")
	@ApiOperation(value = "User Login", notes = "Enter your Email and Password", response = User.class)
	public ResponseEntity<String> loginUser(@RequestBody User user) {
		User registeredUser = userService.loginUser(user);
		if (registeredUser != null) {
			return new ResponseEntity<>("Succesfully Logged in", HttpStatus.CREATED);
		} else
			return new ResponseEntity<>("Unsuccessful!! Wrong Email or Password", HttpStatus.NOT_FOUND);
	}

	/**
	 * This method is only for admin Finding a particular user by userId
	 * 
	 * @param user_id
	 * @return user
	 */
	@GetMapping("/users/{user_id}")
	@ApiOperation(value = "Finding user by id", notes = "Provide an id to find user", response = User.class)
	public ResponseEntity<User> findUserById(
			@ApiParam(value = "ID value for the user you want to retrieve", required = true) @PathVariable("user_id") int user_id) {
		ResponseEntity<User> response = null;
		User existingUser = userService.getUserById(user_id);
		if (existingUser != null) {
			response = new ResponseEntity<>(existingUser, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return response;
	}

	/**
	 * This method is only for admin Finding a particular user by email
	 * 
	 * @param email
	 * @return user
	 */
	@GetMapping("/users")
	@ApiOperation(value = "Finding user by email", notes = "Provide an email to find user", response = User.class)
	public ResponseEntity<User> findUserByEmail(
			@ApiParam(value = "Email value for the user you want to retrieve", required = true) @RequestParam("email") String email) {
		User existingUser = userService.getUserByEmail(email);
		if (existingUser == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(existingUser, HttpStatus.OK);
	}

	/**
	 * This method is only for admin To see All users
	 * 
	 * @return user
	 */
	@GetMapping("/userall")
	@ApiOperation(value = "All users", response = User.class)
	public ResponseEntity<List<User>> findAllUsers() {
		List<User> users = userService.getAllUsers();
		if (!users.isEmpty()) {
			return new ResponseEntity<>(users, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * This method is for admin and user both Adding a user by specifying its role
	 * 
	 * @param user
	 * @return Response Status
	 */
	@PostMapping("/useradd")
	@ApiOperation(value = "Adding user", notes = "Provide all attributes to add user", response = User.class)
	public ResponseEntity<String> saveUser(@RequestBody User user) {
		User existingUser = userService.addUser(user);
		if (existingUser != null) {
			return new ResponseEntity<>(UserConstants.added, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(UserConstants.userAlreadyExists, HttpStatus.CONFLICT);
		}
	}

	/**
	 * This method is only for admin Deleting a user by userId
	 * 
	 * @param user_id
	 * @return Response Status
	 */
	@DeleteMapping("/userdel/{user_id}")
	@ApiOperation(value = "Deleting user", notes = "Provide id to delete user", response = User.class)
	public ResponseEntity<String> deleteUser(
			@ApiParam(value = "ID value for the user you want to delete", required = true) @PathVariable("user_id") int user_id) {
		userService.deleteUser(user_id);
		return new ResponseEntity<>(UserConstants.deleted, HttpStatus.OK);
	}

	/**
	 * This method is for user and admin both Updating user, to make some changes in
	 * your personal account
	 * 
	 * @param user
	 * @return Response Status
	 */
	@PutMapping("/userupd")
	@ApiOperation(value = "Updating user", notes = "Change the attributes you want to user", response = User.class)
	public ResponseEntity<String> updateUser(@RequestBody User user) {
		User existingUser = userService.updateUser(user);
		if (existingUser != null) {
			return new ResponseEntity<>(UserConstants.updated, HttpStatus.CREATED);
		} else
			return new ResponseEntity<>(UserConstants.userNotExists, HttpStatus.NOT_FOUND);
	}

//....................................................................................................................//
//............................................USER METHODS............................................................//

	/**
	 * This method is only for user To follow a particular user
	 * 
	 * @param user_id
	 * @param follower_email
	 * @param email
	 * @return Followers
	 */
	@PostMapping("/follow")
	@ApiOperation(value = "Following", notes = "Following users", response = Followers.class)
	public ResponseEntity<Followers> follow(
			@ApiParam(value = "ID value for the user you want to follow", required = true) @RequestParam("user_id") int user_id,
			@ApiParam(value = "Email id of you", required = true) @RequestParam("follower_email") String follower_email,
			@ApiParam(value = "Email id of user", required = true) @RequestParam("email") String email) {
		Followers follower = followerService.followUser(user_id, email, follower_email);
		return new ResponseEntity<>(follower, HttpStatus.CREATED);
	}

	/**
	 * This method is only for user To unfollow a particular user
	 * 
	 * @param user_id     userId
	 * @param follower_id followerId
	 * @return Response status
	 */
	@DeleteMapping("/unfollow")
	@ApiOperation(value = "Un Following", notes = "Un Following users", response = Followers.class)
	public ResponseEntity<String> unfollow(
			@ApiParam(value = "ID value for the user you want to follow", required = true) @RequestParam("user_id") int user_id,
			@ApiParam(value = "ID value of the follower", required = true) @RequestParam("follower_id") int follower_id) {
		followerService.unFollowUser(follower_id, user_id);
		return new ResponseEntity<>(UserConstants.unfollowing, HttpStatus.CREATED);
	}

	/**
	 * This method is only for users To get all the followers of a particular user
	 * 
	 * @param user_id
	 * @return List of Followers
	 */
	@GetMapping("/trackfollowers")
	@ApiOperation(value = "User Content", notes = "Get a specific user's followers", response = Content.class)
	public ResponseEntity<List<Followers>> findFollowers(
			@ApiParam(value = "ID value of the user you want to track", required = true) @RequestParam("user_id") int user_id) {
		List<Followers> followers = userService.retrieveFollowers(user_id);
		if (!followers.isEmpty()) {
			return new ResponseEntity<>(followers, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
