package com.cpg.pixogramspring.services;

import com.cpg.pixogramspring.models.Comment;

public interface CommentService {

	void deleteComment(int comment_id);

	Comment getComment(int comment_id);

}
