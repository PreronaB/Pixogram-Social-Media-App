package com.cpg.pixogramspring.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Comments By User On Content")
@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(notes = "The unique Id of comment")
	private int comment_id;
	@ApiModelProperty(notes = "The comment/message on content")
	private String comment;
	
	private String username;

	public Comment() {
		super();
	}

	public Comment(String comment,String username) {
		super();
		this.comment = comment;
		this.username=username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getComment_id() {
		return comment_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "Comment [comment_id=" + comment_id + ", comment=" + comment + "]";
	}

}
