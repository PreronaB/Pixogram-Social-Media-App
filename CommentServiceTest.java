package com.cpg.pixogramspring.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cpg.pixogramspring.models.Comment;
import com.cpg.pixogramspring.models.Role;
import com.cpg.pixogramspring.models.User;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
class CommentServiceTest {
	
	User user;
	Comment comment;
	Role role;
	
	@MockBean
	CommentService commentService;
	
	@SuppressWarnings("deprecation")
	
	
	@BeforeAll
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@BeforeEach
	void setUp() throws Exception {
		role = new Role("Admin");
		user=new User("prerona","Abc1234@","prerona@capg.com","f","assam","hi",role);
		comment=new Comment("This is a test comment", "prerona@capg.com");
		
	}
	
	@Test
	void testDeleteComment() {
		commentService.deleteComment(2);
		verify(commentService,times(1)).deleteComment(2);
		assertNotNull(comment);
	}
	@Test
	void testGetComment() {
		commentService.getComment(2);
		verify(commentService,times(1)).getComment(2);
		assertEquals("This is a test comment", comment.getComment());
	}
	

	@AfterEach
	void tearDown() throws Exception {
		user = null;
		comment = null;
		role=null;
	}


}
