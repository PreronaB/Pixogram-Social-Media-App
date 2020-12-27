package com.cpg.pixogramspring.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.hibernate.tool.schema.internal.exec.ScriptSourceInputNonExistentImpl;
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
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.multipart.MultipartFile;

import com.cpg.pixogramspring.entities.Comment;
import com.cpg.pixogramspring.entities.Content;
import com.cpg.pixogramspring.entities.Followers;
import com.cpg.pixogramspring.entities.Role;
//import com.cpg.pixogramspring.entities.Role;
import com.cpg.pixogramspring.entities.User;
import com.cpg.pixogramspring.exceptions.ValidationException;
import com.cpg.pixogramspring.repositories.CommentRepository;
import com.cpg.pixogramspring.repositories.ContentRepository;
import com.cpg.pixogramspring.repositories.FollowersRepository;
import com.cpg.pixogramspring.repositories.UserRepository;
import com.cpg.pixogramspring.services.ContentService;
import com.cpg.pixogramspring.services.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private ContentService contentService;
	@Autowired
	private ContentRepository contentRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private FollowersRepository followersRepository;

//.............................................................................................................//
//...........................................ADMIN AND USER METHOD.....................................................//

	@GetMapping("/pixologin")
	@ApiOperation(value = "User Login", notes = "enter your email and password", response = User.class)
	public ResponseEntity<String> loginUser(
			@ApiParam(value = "Your Email Id to Login", required = true) @RequestParam("email") String email,
			@ApiParam(value = "Your password to Login", required = true) @RequestParam("password") String password) {
		User registeredUser = userRepository.findByEmailAndPassword(email, password);
		if (registeredUser != null) {
			return new ResponseEntity<String>("Succesfully Logged in", HttpStatus.CREATED);
		} else
			return new ResponseEntity<>("Unsuccessful!! Wrong Email or Password", HttpStatus.NOT_FOUND);
	}

//.............................................................................................................//
//...........................................ADMIN METHODS.....................................................//

	@GetMapping("/pixouser/{user_id}")
	@ApiOperation(value = "Finding user by id", notes = "Provide an id to find user", response = User.class)
	public ResponseEntity<User> findUserById(
			@ApiParam(value = "ID value for the user you want to retrieve", required = true) @PathVariable("user_id") int user_id) {
		ResponseEntity<User> response = null;
		System.out.println("Recieved id on path: " + user_id);
		// code here to fetch user by id
		Optional<User> user = userRepository.findById(user_id);
		if (user.isPresent()) {
			User userFound = user.get();
			response = new ResponseEntity<>(userFound, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@GetMapping("/pixouser")
	@ApiOperation(value = "Finding user by email", notes = "Provide an email to find user", response = User.class)
	public ResponseEntity<User> findUserByEmail(
			@ApiParam(value = "Email value for the user you want to retrieve", required = true) @RequestParam("email") String email) {
		// ResponseEntity<User> response = null;
		// System.out.println(email);
		User existingUser = userRepository.findByEmail(email);
		if (existingUser == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(existingUser, HttpStatus.OK);
	}

	@GetMapping("/pixouserall")
	@ApiOperation(value = "All users", response = Iterable.class)
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	@PostMapping("/pixouseradd")
	@ApiOperation(value = "Adding user", notes = "Provide all attributes to add user", response = User.class)
	public ResponseEntity<String> addUser(@RequestBody User user) throws ValidationException {
		User existingUser = userRepository.findByEmail(user.getEmail());
		if (existingUser != null) {
			return new ResponseEntity<String>("User already exists!!", HttpStatus.CONFLICT);
		}
		Role role = user.getRole();
		Role existingRole = userRepository.findRole(user.getRole().getRolename());
		if (existingRole.getRolename().equals(role.getRolename())) {
			user.setRole(existingRole);
		}
		userService.addUser(user);
		ResponseEntity<String> re = new ResponseEntity<>("successfully registered user!!", HttpStatus.CREATED);
		return re;
	}

	@PostMapping("/pixousers")
	@ApiOperation(value = "Adding users", notes = "Provide all attributes to add user", response = User.class)
	public List<User> addUsers(@RequestBody List<User> users) {
		return userRepository.saveAll(users);
	}

	@DeleteMapping("/pixouserdel/{user_id}")
	@ApiOperation(value = "Deleting user", notes = "Provide id to delete user", response = User.class)
	public ResponseEntity<String> deleteUser(
			@ApiParam(value = "ID value for the user you want to delete", required = true) @PathVariable("user_id") int user_id) {
		Optional<User> user = userRepository.findById(user_id);
		if (user.isPresent()) {
			userRepository.deleteById(user_id);
			return new ResponseEntity<String>("Successfully deleted!!", HttpStatus.CREATED);
		} else
			return new ResponseEntity<>("User does not exists", HttpStatus.NOT_FOUND);
	}

	@PutMapping("/pixouserupd")
	@ApiOperation(value = "Updating user", notes = "Change the attributes you want to user", response = User.class)
	public ResponseEntity<String> updateUser(@RequestBody User userObj) {
		// User existingUser = userRepository.findByEmail(userObj.getEmail());
		Optional<User> user = userRepository.findById(userObj.getUser_id());
		if (user.isPresent()) {
			userRepository.save(userObj);
			return new ResponseEntity<String>("User updated", HttpStatus.CREATED);
		} else
			return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
	}

//..............................................................................................................//
//............................................USER METHODS......................................................//

	@PostMapping("/upload")
	@ApiOperation(value = "Uploading a file", notes = "Add the file and provide a caption", response = Content.class)
	public ResponseEntity<String> uploadFile(
			@ApiParam(value = "Select a file you want to upload", required = true) @RequestParam("file") MultipartFile file,
			@ApiParam(value = "Caption for file you are uploading", required = true) @RequestParam("caption") String caption,
			@ApiParam(value = "ID value for the user you want to upload for", required = true) @RequestParam("user_id") int user_id)
			throws IllegalStateException, IOException {
		Content existingContent = contentRepository.findByCaption(caption);
		User existingUser = contentRepository.findUser(user_id);
		if (existingContent != null) {
			return new ResponseEntity<String>("Content already exists!!", HttpStatus.CONFLICT);
		}
		if (existingUser == null) {
			return new ResponseEntity<String>("User does not exists!!", HttpStatus.CONFLICT);
		}
		contentService.uploadFile(file, caption, user_id);
		ResponseEntity<String> re = new ResponseEntity<>("Successfully added!!", HttpStatus.CREATED);
		return re;
	}

	@GetMapping("/upload/{content_id}")
	@ApiOperation(value = "Find a file by Id", notes = "Provide an id to find file", response = Content.class)
	public ResponseEntity<Content> findContent(
			@ApiParam(value = "ID value for the content you want to retrieve", required = true) @PathVariable("content_id") int content_id) {
		ResponseEntity<Content> response = null;
		Optional<Content> content = contentRepository.findById(content_id);
		if (content.isPresent()) {
			Content foundContent = content.get();
			response = new ResponseEntity<>(foundContent, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@GetMapping("/uploadsall")
	@ApiOperation(value = "View whole content", response = Content.class)
	public List<Content> allContent(Content content) {
		return contentRepository.findAll();
	}

	@DeleteMapping("/uploaddel/{content_id}")
	@ApiOperation(value = "Deleting a file", notes = "Provide an id to delete file", response = Content.class)
	public ResponseEntity<String> deleteContent(
			@ApiParam(value = "ID value for the content you want to delete", required = true) @PathVariable("content_id") int content_id) {
		Optional<Content> content = contentRepository.findById(content_id);
		if (content.isPresent()) {
			contentRepository.deleteById(content_id);
			return new ResponseEntity<String>("Successfully deleted!!", HttpStatus.CREATED);
		} else
			return new ResponseEntity<String>("Image/video does not exists", HttpStatus.NOT_FOUND);
	}

//	@PutMapping("/uploadupd")
//	@ApiOperation(value = "Updating info", notes = "Change the attributes you want", response = Content.class)
//	public ResponseEntity<String> updateContent(@RequestParam("content_id") int content_id,
//			@RequestParam("user_id") int user_id) {
//		// User existingUser = contentRepository.findUser(user_id);
//		Content contentObj = contentRepository.findContent(user_id);
//		if (contentObj != null) {
//			contentRepository.save(contentObj);
//			return new ResponseEntity<String>("Changes updated", HttpStatus.CREATED);
//		} else
//			return new ResponseEntity<>("Image/Video not found", HttpStatus.NOT_FOUND);
//	}

//.............................................................................................................//
//.........................................USER METHODS ON CONTENTS............................................//

	@PostMapping("/comment")
	@ApiOperation(value = "Adding Comment", notes = "Commenting on images and videos", response = Content.class)
	public ResponseEntity<String> addComment(
			@ApiParam(value = "ID value for the content you want to add comment", required = true) @RequestParam("content_id") int content_id,
			@ApiParam(value = "ID value for the user you want to add comment", required = true) @RequestParam("user_id") int user_id,
			@ApiParam(value = "String value the comment you want to add", required = true) @RequestParam("comment") String comment) {
		Optional<Content> existingContent = contentRepository.findById(content_id);
		if (existingContent != null) {
			User existingUser = contentRepository.findUser(user_id);
			if (existingUser != null) {
				contentService.commentAdd(user_id, content_id, comment);
				return new ResponseEntity<>("Comment added", HttpStatus.CREATED);
			}
			return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Content does not exist", HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/commentdel/{comment_id}")
	@ApiOperation(value = "Deleting a file", notes = "Provide an id to delete file", response = Comment.class)
	public ResponseEntity<String> deleteComment(
			@ApiParam(value = "ID value for the comment you want to delete", required = true) @PathVariable("comment_id") int comment_id) {
		Optional<Comment> comment = commentRepository.findById(comment_id);
		if (comment.isPresent()) {
			commentRepository.deleteById(comment_id);
			return new ResponseEntity<String>("Successfully deleted!!", HttpStatus.CREATED);
		} else
			return new ResponseEntity<String>("Image/video does not exists", HttpStatus.NOT_FOUND);
	}

	@PostMapping("/like/add")
	@ApiOperation(value = "Adding Likes", notes = "Liking the images and videos", response = Content.class)
	public ResponseEntity<String> addLike(
			@ApiParam(value = "ID value for the user you want to delete", required = true) @RequestParam("user_id") int user_id,
			@ApiParam(value = "ID value for the content you want to delete", required = true) @RequestParam("content_id") int content_id) {
		Content existingContent = contentRepository.findContentById(content_id);
		User existingUser = contentRepository.findUser(user_id);
		if (existingContent != null) {
			if (existingUser != null) {
				contentService.addLikes(content_id);
				return new ResponseEntity<>("Like Updated", HttpStatus.CREATED);
			}
			return new ResponseEntity<>("User does not exist with respect to Content", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Content does not exist", HttpStatus.NOT_FOUND);
	}

	@PostMapping("/dislike/add")
	@ApiOperation(value = "Adding Dislikes", notes = "Disliking the images and videos", response = Content.class)
	public ResponseEntity<String> addDislike(@RequestParam("user_id") int user_id,
			@RequestParam("content_id") int content_id) {
		Content existingContent = contentRepository.findContentById(content_id);
		User existingUser = contentRepository.findUser(user_id);
		if (existingContent != null) {
			if (existingUser != null) {
				contentService.addDislikes(content_id);
				return new ResponseEntity<>("Dislike Updated", HttpStatus.CREATED);
			}
			return new ResponseEntity<>("User does not exist with respect to Content", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Content does not exist", HttpStatus.NOT_FOUND);
	}
	/*----------------------------------------editing view methods----------------------------------------*/
	@GetMapping("/like/view")
	@ApiOperation(value = "Viewing Likes", notes = "Viewing number of likes on the images and videos", response = Content.class)
	public int viewLikes(@RequestParam("user_id") int user_id,
			@RequestParam("content_id") int content_id) throws Exception {
		User existingUser = contentRepository.findUser(user_id);
		Content existingContent = contentRepository.findContentById(content_id);
		if (existingContent != null) {
			if (existingUser != null) {
				return existingContent.getLike();
			}
			throw new Exception("User does not exist with respect to Content");
		}
		throw new Exception("Content does not exist");
	}
	
	@GetMapping("/dislike/view")
	@ApiOperation(value = "Viewing Dislikes", notes = "Viewing number of dislikes on the images and videos", response = Content.class)
	public int viewDislikes(@RequestParam("user_id") int user_id,
			@RequestParam("content_id") int content_id) throws Exception {
		User existingUser = contentRepository.findUser(user_id);
		Content existingContent = contentRepository.findContentById(content_id);
		if (existingContent != null) {
			if (existingUser != null) {
				return existingContent.getDislike();
			}
			throw new Exception("User does not exist with respect to Content");
		}
		throw new Exception("Content does not exist");
	}
	
	@GetMapping("/comments/view")
	@ApiOperation(value = "Viewing Comments", notes = "Viewing comments on the images and videos", response = Content.class)
	public List<Comment> viewComments(@RequestParam("user_id") int user_id,
			@RequestParam("content_id") int content_id) throws Exception {
		User existingUser = contentRepository.findUser(user_id);
		Content existingContent = contentRepository.findContentById(content_id);
		if (existingContent != null) {
			if (existingUser != null) {
				return existingContent.getComment();
			}
			throw new Exception("User does not exist with respect to Content");
		}
		throw new Exception("Content does not exist");
	}
	/*--------------follower class methods-----------------------------*/
	
	@PostMapping("/follower/follow")
	public void followUser(@RequestParam("email") String email,
			@RequestParam("user_id") int user_id) throws Exception {
		Followers follower=new Followers();
		User existingUser = userRepository.findByEmail(email);
		if(existingUser!=null) {
			Optional<User> existingUserToFollow=userRepository.findById(user_id);
			if(existingUserToFollow!=null) {
				//Followers follower=new Followers();
				
				follower.setEmail(existingUserToFollow.get().getEmail());
				//existingUserToFollow.get().getEmail();
				//existingUser.setEmail(email);
				
//				Followers follower=existingUserToFollow.get().getEmail();
//				existingUser.setFollowers(followers);
			}
			else throw new Exception("The user you want to follow does not exist");
			
		}
		else throw new Exception("You need to be a user to be a follower");
		
	}
	
//	@DeleteMapping("/follower/unfollow")
//	public void unfollowUser(@RequestParam("email") String email,
//			@RequestParam("user_id") int user_id) throws Exception {
//		User existingUser = userRepository.findByEmail(email);
//		if(existingUser!=null) {
//			Optional<User> existingUserToFollow=userRepository.findById(user_id);
//			if(existingUserToFollow!=null) {
//				followersRepository.deleteById(user_id);
//				
//			}
//			throw new Exception("The user you want to follow does not exist");
//			
//		}
//		throw new Exception("You need to be a user to be a follower");
//		
//	}
}
