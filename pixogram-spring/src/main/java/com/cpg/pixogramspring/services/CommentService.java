package com.cpg.pixogramspring.services;

import com.cpg.pixogramspring.entities.Comment;
import com.cpg.pixogramspring.exceptions.CommentNotExistsException;

public interface CommentService {
	public void deleteComment(int comment_id) throws CommentNotExistsException;
	
	public Comment getComment(int comment_id) throws CommentNotExistsException;

}
