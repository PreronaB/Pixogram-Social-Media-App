package com.cpg.pixogramspring.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cpg.pixogramspring.models.Followers;
import com.cpg.pixogramspring.models.Role;
import com.cpg.pixogramspring.models.User;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
class FollowerServiceTest {

	Followers follower;
	User user;
	Role role;
	
	@MockBean
	FollowerService followerService;
	
	@SuppressWarnings("deprecation")
	@BeforeAll
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@BeforeEach
	void setUp() throws Exception {
		role= new Role("User");
		user= new User("Salonee", "Abc1234@", "Abc@gmail.com", "female", "Odisha", "Scorpio", role);
		follower = new Followers("Pqrst@gmail.com", "Abc@gmail.com");
	}

	@AfterEach
	void tearDown() throws Exception {
		user = null;
		role = null;
		follower = null;
	}

	@Test
	void testFollowUser() {
		followerService.followUser(1,"Pqrst@gmail.com", "Abc@gmail.com");
		verify(followerService, times(1)).followUser(1, "Pqrst@gmail.com", "Abc@gmail.com");
		assertEquals("Pqrst@gmail.com", follower.getFollower__email());
	}
	

	@Test
	void testUnFollowUser() {
		followerService.unFollowUser(2, 1);
		verify(followerService, times(1)).unFollowUser(2, 1);
		assertNotNull(follower);
		
	}

	

}
