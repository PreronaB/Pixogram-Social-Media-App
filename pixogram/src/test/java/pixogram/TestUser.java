package pixogram;

import static org.junit.Assert.*;
import java.io.File;
import javax.activation.MimetypesFileTypeMap;
import org.junit.Test;
import pixogram.sprint1.Content;
import pixogram.sprint1.Role;
import pixogram.sprint1.User;
import pixogram.sprint1.UserRepository;
import pixogram.sprint1.UserService;
import pixogram.sprint1.ValidationException;
import pixogram.sprint1.Content;

public class TestUser {

	UserService us = new UserService();
	UserRepository ur = new UserRepository();

	@Test
	public void testAddUser() throws ValidationException {
		Role role = new Role("Admin");
		Role role2 = new Role("General User");
		User user = new User("riyag", "Riya@123", "riya@gmail.com", "female", "punjab", "Aries", role);
		User user2 = new User("prerna", "PrernA123#", "prerna123@gmail.com", "female", "punjab", "Scorpio", role2);
		User user3 = new User("Salonee", "Salone12@", "salonee32@gmail.com", "female", "punjab", "Scorpio", role);
		User user4 = new User("nidhi", "Nidhi113#", "nidhi321@gmail.com", "female", "punjab", "Scorpio", role2);
		User user5 = new User("Sushmita", "Sush12#er", "sushm@ernm.com", "female", "rajasthan", "Aries", role);
		us.addUser(user);
		us.addUser(user2);
		us.addUser(user3);
		us.addUser(user4);
		us.addUser(user5);
		//assertTrue(check);
	}
	
//	@Test
	public void testAddUserNegative() throws ValidationException{
		Role role = new Role("Admin");
		User user5 = new User("Sushmita", "Sush12@er", "sushm@ernm.com", "f", "rajasthan", "Aries", role);
		//boolean check= us.addUser(user5);
		//assertFalse(check);
	}

	// @Test
	public void testLogin() {
		boolean check = ur.login("riya@gmail.com", "Riya@123");
		assertTrue(check);
	}

	// @Test
	public void testLoginNegative() {
		boolean check = ur.login("riya@gmail.com", "Riya@12");
		assertFalse(check);
	}

	// @Test
	public void testViewUser() {
		//boolean check=ur.viewUserByEmail("riya@gmail.com");
		//ur.viewAllUsers();
		//assertTrue(check);
	}
	
	//@Test
		public void testViewUserNegative() {
			ur.viewUserByEmail("salonee32@gmail.com");
			//ur.viewAllUsers();
			//assertFalse(check);
		}

	// @Test
	public void testEditUser() {
		us.editUser(1, "riya13@gmail.com");
	}

	// @Test
	public void testDelUser() {
		Role role = new Role("Admin");
		User user = new User("Sushmita", "Sush12#er", "sushm@ernm.com", "female", "rajasthan", "Aries", role);
		ur.deleteUser(7, "sushm@ernm.com");
		// ur.deleteUser(user);
	}

/*....................................................................................................................*/
	
	
	// @Test
	public void testaddImage() {
		String filepath1 = "E:/Sprint/image4.png";
		File f1 = new File(filepath1);
		String mimetype = new MimetypesFileTypeMap().getContentType(f1);
		String type = mimetype.split("/")[0];
		String path = f1.getAbsolutePath();
		if (type.equals("image")) {
			System.out.println("It's an image");
			System.out.println("File added: " + path);
		} else
			System.out.println("It's NOT an image");
		Content content1 = new Content("new image", f1, path);
		ur.addContent(content1);
	}

	// @Test
	public void testaddVideo() {
		String filepath = "E:/Sprint/Falls9.mov";
		File f = new File(filepath);        
		String mimetype = new MimetypesFileTypeMap().getContentType(f);
		String type = mimetype.split("/")[0];
		String path = f.getAbsolutePath();
		if (type.equals("video")) {
			System.out.println("It's a video");
			System.out.println("File added: " + path);
		} else
			System.out.println("It's NOT a video");
		Content content = new Content("new video", f, path);
		ur.addContent(content);
	}

	// @Test
	public void testViewContent() {
		ur.viewContentByCaption("new");
		//ur.viewAllContents();
	}
	
	// @Test
	public void testDelContent() {
		ur.deleteContent(9, "new");
	}

	// @Test
	public void testEditContent() {
		us.editContent(9, "new");
	}
	
}
