package com.cpg.pixogramspring.services;

import com.cpg.pixogramspring.exceptions.NotFoundException;
import com.cpg.pixogramspring.models.Comment;

public interface CommentService {
	public void deleteComment(int comment_id) throws NotFoundException;
	
	public Comment getComment(int comment_id) throws NotFoundException;

}
