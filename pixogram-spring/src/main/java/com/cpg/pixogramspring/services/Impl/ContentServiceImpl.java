package com.cpg.pixogramspring.services.Impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cpg.pixogramspring.entities.Comment;
import com.cpg.pixogramspring.entities.Content;
import com.cpg.pixogramspring.entities.User;
import com.cpg.pixogramspring.exceptions.ContentNotFoundException;
import com.cpg.pixogramspring.exceptions.UserNotFoundException;
import com.cpg.pixogramspring.repositories.ContentRepository;
import com.cpg.pixogramspring.repositories.UserRepository;
import com.cpg.pixogramspring.services.ContentService;

@Service
public class ContentServiceImpl implements ContentService {
	
	@Value("User not found!!")
	private String userNotFoundMessage;
	@Value("Content not found!!")
	private String contentNotFoundMessage;
	@Autowired
	private ContentRepository contentRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Content uploadFile(MultipartFile file, String caption, int user_id) throws UserNotFoundException,IllegalStateException, IOException {
		long append= System.nanoTime();
		String filen=file.getOriginalFilename();
		String filename= append + "_" + filen;
		String filetype=file.getContentType();
		User existingUser=userRepository.findUser(user_id);
		file.transferTo(new File("E:\\Sprint\\uploads\\"+file.getOriginalFilename()));
		//Content existingContent = contentRepository.findByCaption(caption);
		try {
		Content content=new Content(caption,filename,filetype);
		if(existingUser==null) {
			throw new UserNotFoundException(userNotFoundMessage);
		}
		if((user_id)==(existingUser.getUser_id())){
			content.setUser(existingUser);
		}
		return contentRepository.save(content);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Content findContent(int content_id) throws ContentNotFoundException {
		Optional<Content> content = contentRepository.findById(content_id);
		if (content.isPresent()) {
			Content foundContent = content.get();
			return foundContent;
	}
		else {
			throw new ContentNotFoundException(contentNotFoundMessage);
		}
	}

	@Override
	public List<Content> allContent() {
		return contentRepository.findAll();
	}

	@Override
	public void deleteContent(int content_id) throws ContentNotFoundException {
		Optional<Content> content = contentRepository.findById(content_id);
		if(!content.isPresent()) {
			throw new ContentNotFoundException(contentNotFoundMessage);
		}
		else {
			contentRepository.deleteById(content_id);
		}
	}
	
////	public Content updateContent(Content content) throws ContentNotFoundException {
////		Content existingContent= contentRepository.findContentById(content.getContent_id());
////		if(existingContent!=null) {
////			return contentRepository.save(content);
////		}
////		else {
////			throw new ContentNotFoundException(contentNotFoundMessage);
////		}
//		
//	}
	
	@Override
	public Content addComment(int user_id,int content_id,String comment) {
		User existingUser=userRepository.findUser(user_id);
		 String email=existingUser.getEmail();
		Content existingContent=contentRepository.findContentById(content_id);
		Comment message=new Comment(comment,email);
		List<Comment> comments=new ArrayList<>();
		if((existingContent!=null)) {
			comments= existingContent.getComment();
			comments.add(message);
			existingContent.setComment(comments);
		}
		return contentRepository.save(existingContent);
	}
	
	@Override
	public Content addLikes(int content_id) throws ContentNotFoundException {
		Content existingContent=contentRepository.findContentById(content_id);
		int like=existingContent.getLike();
		// contentRepository.countLikes();
		if(existingContent!=null) {
			like+=1;
			existingContent.setLike(like);
		}	
		return contentRepository.save(existingContent);
//		else {
//			throw new ContentNotFoundException("Content Not Found");
//		}
	}
	
	@Override
	public Content addDislikes(int content_id) throws ContentNotFoundException {
		Content existingContent=contentRepository.findContentById(content_id);
		int dislike= existingContent.getDislike();
		if(existingContent!=null) {
			dislike+=1;
			existingContent.setDislike(dislike);
		}
		return contentRepository.save(existingContent);
	}

	
}
