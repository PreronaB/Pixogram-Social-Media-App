package com.cpg.pixogramspring.services.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpg.pixogramspring.entities.Comment;
import com.cpg.pixogramspring.exceptions.CommentNotExistsException;
import com.cpg.pixogramspring.repositories.CommentRepository;
import com.cpg.pixogramspring.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	CommentRepository commentRepository;
	
	public void deleteComment(int comment_id) throws CommentNotExistsException {
		Optional<Comment> comment=commentRepository.findById(comment_id);
		if(!comment.isPresent()) {
			throw new CommentNotExistsException("Not Exists");
		}
		commentRepository.deleteById(comment_id);
	}

	@Override
	public Comment getComment(int comment_id) throws CommentNotExistsException {
		Optional<Comment> comment=commentRepository.findById(comment_id);
		if(comment.isPresent()) {
			Comment foundComment=comment.get();
			return foundComment;
		}
		else {
			throw new CommentNotExistsException("Not Exists");
		}	
	}

}
