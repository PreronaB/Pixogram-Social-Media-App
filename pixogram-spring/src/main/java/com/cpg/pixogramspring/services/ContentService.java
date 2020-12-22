package com.cpg.pixogramspring.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cpg.pixogramspring.entities.Comment;
import com.cpg.pixogramspring.entities.Content;
import com.cpg.pixogramspring.entities.User;
import com.cpg.pixogramspring.repositories.ContentRepository;

@Service
public class ContentService {
	
	@Autowired
	private ContentRepository contentRepository;
	
	public Content uploadFile(MultipartFile file, String caption, int user_id) throws IllegalStateException, IOException {
		long append= System.nanoTime();
		String filen=file.getOriginalFilename();
		String filename= append + "_" + filen;
		String filetype=file.getContentType();
		User existingUser=contentRepository.findUser(user_id);
		file.transferTo(new File("E:\\Sprint\\uploads\\"+file.getOriginalFilename()));
		try {
		Content content=new Content(caption,filename,filetype);
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
	
	public Content commentAdd(int user_id,int content_id,String comment) {
		 User existingUser = contentRepository.findUser(user_id);
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
	
	public Content addLikes(int content_id) {
		Content existingContent=contentRepository.findContentById(content_id);
		int like=existingContent.getLike();
		// contentRepository.countLikes();
		if(existingContent!=null) {
			like+=1;
			existingContent.setLike(like);
		}	
		
		return contentRepository.save(existingContent);
	}
	
	public Content addDislikes(int content_id) {
		Content existingContent=contentRepository.findContentById(content_id);
		int dislike= existingContent.getDislike();
		if(existingContent!=null) {
			dislike+=1;
		}
		existingContent.setDislike(dislike);
		return contentRepository.save(existingContent);
	}
	
}
