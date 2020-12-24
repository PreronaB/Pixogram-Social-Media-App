package com.cpg.pixogramspring.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cpg.pixogramspring.entities.Comment;
import com.cpg.pixogramspring.entities.Content;
import com.cpg.pixogramspring.entities.Role;
//import com.cpg.pixogramspring.entities.Role;
import com.cpg.pixogramspring.entities.User;
import com.cpg.pixogramspring.exceptions.CommentNotExistsException;
import com.cpg.pixogramspring.exceptions.ContentNotFoundException;
import com.cpg.pixogramspring.exceptions.UserAlreadyExistsException;
import com.cpg.pixogramspring.exceptions.UserNotFoundException;
import com.cpg.pixogramspring.exceptions.ValidationException;
import com.cpg.pixogramspring.repositories.CommentRepository;
import com.cpg.pixogramspring.repositories.ContentRepository;
import com.cpg.pixogramspring.repositories.UserRepository;
import com.cpg.pixogramspring.services.CommentService;
import com.cpg.pixogramspring.services.ContentService;
import com.cpg.pixogramspring.services.UserService;
import com.cpg.pixogramspring.services.Impl.ContentServiceImpl;
import com.cpg.pixogramspring.services.Impl.UserServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api")
@Api(value = "User", tags = { "UserAPI" })
public class UserController {

	@Autowired
	private ContentService contentService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private UserService userService;

//..........................................................................................................................//
//...........................................ADMIN AND USER METHOD..........................................................//

	@GetMapping("/pixologin")
	@ApiOperation(value = "User Login", notes = "enter your email and password", response = User.class)
	public ResponseEntity<String> loginUser(
			@ApiParam(value = "Your Email Id to Login", required = true) @RequestParam("email") String email,
			@ApiParam(value = "Your password to Login", required = true) @RequestParam("password") String password) {
		User registeredUser = userService.loginUser(email, password);
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
		User existingUser = userService.getUserById(user_id);
		if (existingUser != null) {
			response = new ResponseEntity<>(existingUser, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@GetMapping("/pixouser")
	@ApiOperation(value = "Finding user by email", notes = "Provide an email to find user", response = User.class)
	public ResponseEntity<User> findUserByEmail(
			@ApiParam(value = "Email value for the user you want to retrieve", required = true) @RequestParam("email") String email) {
		User existingUser = userService.getUserByEmail(email);
		if (existingUser == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(existingUser, HttpStatus.OK);
	}

	@GetMapping("/pixouserall")
	@ApiOperation(value = "All users", response = User.class)
	public ResponseEntity<List<User>> findAllUsers() {
		List<User> users = userService.getAllUsers();
		if (!users.isEmpty()) {
			return new ResponseEntity<>(users, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/pixouseradd")
	@ApiOperation(value = "Adding user", notes = "Provide all attributes to add user", response = User.class)
	public ResponseEntity<String> saveUser(@RequestBody User user)
			throws ValidationException, UserAlreadyExistsException {
		User existingUser = userService.addUser(user);
		if (existingUser != null) {
			return new ResponseEntity<>("Successfully registered user!!", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("User already exists!!", HttpStatus.CONFLICT);
		}
	}

	@DeleteMapping("/pixouserdel/{user_id}")
	@ApiOperation(value = "Deleting user", notes = "Provide id to delete user", response = User.class)
	public ResponseEntity<String> deleteUser(
			@ApiParam(value = "ID value for the user you want to delete", required = true) @PathVariable("user_id") int user_id)
			throws UserNotFoundException {
		userService.deleteUser(user_id);
		return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
	}

	@PutMapping("/pixouserupd")
	@ApiOperation(value = "Updating user", notes = "Change the attributes you want to user", response = User.class)
	public ResponseEntity<String> updateUser(@RequestBody User userObj) throws UserNotFoundException {
		User existingUser = userService.updateUser(userObj);
		if (existingUser != null) {
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
			throws IllegalStateException, IOException, UserNotFoundException {
		//Content existingContent = contentService.findContent(content_id);
			User existingUser=userService.getUserById(user_id);
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
			@ApiParam(value = "ID value for the content you want to retrieve", required = true) @PathVariable("content_id") int content_id) throws ContentNotFoundException {
		ResponseEntity<Content> response = null;
		//Optional<Content> content = contentRepository.findById(content_id);
		Content content=contentService.findContent(content_id);
		if (content!=null) {
			response = new ResponseEntity<>(content, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@GetMapping("/uploadsall")
	@ApiOperation(value = "View whole content", response = Content.class)
	public ResponseEntity<List<Content>> allContent() {
		List<Content> contents= contentService.allContent();
		if(!contents.isEmpty()) {
			return new ResponseEntity<>(contents, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/uploaddel/{content_id}")
	@ApiOperation(value = "Deleting a file", notes = "Provide an id to delete file", response = Content.class)
	public ResponseEntity<String> deleteContent(
			@ApiParam(value = "ID value for the content you want to delete", required = true) @PathVariable("content_id") int content_id) throws ContentNotFoundException {
			contentService.deleteContent(content_id);
			return new ResponseEntity<String>("Successfully deleted!!", HttpStatus.CREATED);
	}

//	@PutMapping("/uploadupd")
//	@ApiOperation(value = "Updating info", notes = "Change the attributes you want", response = Content.class)
//	public ResponseEntity<String> updateContent(@RequestParam("content_id") int content_id,
//			@RequestParam("user_id") int user_id) {
//		// User existingUser = contentRepository.findUser(user_id);
//		Content contentObj = contentService.updateContent(content_id);
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
			@ApiParam(value = "String value the comment you want to add", required = true) @RequestParam("comment") String comment) throws ContentNotFoundException {
		Content existingContent = contentService.findContent(content_id);
		if (existingContent != null) {
			User existingUser=userService.getUserById(user_id);
			if (existingUser != null) {
				contentService.addComment(user_id, content_id, comment);
				return new ResponseEntity<>("Comment added", HttpStatus.CREATED);
			}
			return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Content does not exist", HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/commentdel/{comment_id}")
	@ApiOperation(value = "Deleting a file", notes = "Provide an id to delete file", response = Comment.class)
	public ResponseEntity<String> deleteComment(
			@ApiParam(value = "ID value for the comment you want to delete", required = true) @PathVariable("comment_id") int comment_id) throws CommentNotExistsException {
			commentService.deleteComment(comment_id);
			return new ResponseEntity<String>("Successfully deleted!!", HttpStatus.CREATED);
	}
	
	@GetMapping("/comment/{comment_id}")
	@ApiOperation(value = "Finding comment by id", notes = "Provide an id to find comment", response = User.class)
	public ResponseEntity<Comment> findCommentById(
			@ApiParam(value = "ID value for the comment you want to retrieve", required = true) @PathVariable("comment_id") int comment_id) throws CommentNotExistsException {
		ResponseEntity<Comment> response = null;
		Comment existingComment=commentService.getComment(comment_id);
		if (existingComment != null) {
			response = new ResponseEntity<>(existingComment, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@PostMapping("/like")
	@ApiOperation(value = "Adding Likes", notes = "Liking the images and videos", response = Content.class)
	public ResponseEntity<String> addLike(
			@ApiParam(value = "ID value for the user you want to delete", required = true) @RequestParam("user_id") int user_id,
			@ApiParam(value = "ID value for the content you want to delete", required = true) @RequestParam("content_id") int content_id)
			throws ContentNotFoundException {
		Content existingContent = contentService.findContent(content_id);
		User existingUser=userService.getUserById(user_id);
		if (existingContent != null) {
			if (existingUser != null) {
				contentService.addLikes(content_id);
				return new ResponseEntity<>("Like Updated", HttpStatus.CREATED);
			}
			return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Content does not exist", HttpStatus.NOT_FOUND);
	}

	@PostMapping("/dislike")
	@ApiOperation(value = "Adding Dislikes", notes = "Disliking the images and videos", response = Content.class)
	public ResponseEntity<String> addDislike(
			@ApiParam(value = "ID value for the user you want to delete", required = true) @RequestParam("user_id") int user_id,
			@ApiParam(value = "ID value for the user you want to delete", required = true) @RequestParam("content_id") int content_id)
			throws ContentNotFoundException {
		Content existingContent = contentService.findContent(content_id);
		User existingUser=userService.getUserById(user_id);
		if (existingContent != null) {
			if (existingUser != null) {
				contentService.addDislikes(content_id);
				return new ResponseEntity<>("Dislike Updated", HttpStatus.CREATED);
			}
			return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Content does not exist", HttpStatus.NOT_FOUND);
	}
}
