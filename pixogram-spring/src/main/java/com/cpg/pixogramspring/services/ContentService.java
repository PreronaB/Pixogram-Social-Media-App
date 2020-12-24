package com.cpg.pixogramspring.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cpg.pixogramspring.entities.Content;
import com.cpg.pixogramspring.exceptions.ContentNotFoundException;
import com.cpg.pixogramspring.exceptions.UserNotFoundException;

public interface ContentService {
	
	public Content uploadFile(MultipartFile file, String caption, int user_id) throws IllegalStateException, IOException, UserNotFoundException;
	
	public Content findContent(int content_id) throws ContentNotFoundException;
	
	public List<Content> allContent();
	
	public void deleteContent(int content_id) throws ContentNotFoundException;
	
	//public Content updateContent(Content content) throws ContentNotFoundException;
	
	public Content addComment(int user_id,int content_id,String comment);
	
	public Content addLikes(int content_id) throws ContentNotFoundException;
	
	public Content addDislikes(int content_id) throws ContentNotFoundException;

}
