package com.cpg.pixogramspring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cpg.pixogramspring.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}