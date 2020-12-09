package pixogram.sprint1;

import java.util.List;

import javax.persistence.CascadeType;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
//import pixogram.sprint1.User;
import javax.activation.MimetypesFileTypeMap;
import java.io.File;

public class UserRepository {
	private EntityManager em;
	{
		EntityManagerFactory entitymanagerfactory = Persistence.createEntityManagerFactory("test");
		em = entitymanagerfactory.createEntityManager();
	}

	public boolean login(String email, String password) {
		// User_user foundUser = em.find(User_user.class, 3);
		// String email= user.getemail();
		// String password = user.getPassword();
		TypedQuery<User> query = em.createQuery("select u from User u where u.email=:emailparam ", User.class);
		query.setParameter("emailparam", email);
		User existingUser = query.getSingleResult();
		if (existingUser.getEmail().equals(email) && existingUser.getPassword().equals(password)) {
			System.out.println("Successfully login");
			return true;
		} else {
			System.out.println("Unable to Login, use correct email or password");
			return false;
		}

	}

	
/*....................................Admin methods not for User................................................*/
	 

	// adding user
	public void addUser(User user) throws ValidationException {
		em.getTransaction().begin();
		/*
		 * TypedQuery<Role> query =
		 * em.createQuery("select r from Role r where r.rolename=:roleparam",
		 * Role.class); query.setParameter("roleparam", user.getRole().getRolename());
		 * Role role = user.getRole(); Role existingRole = query.getSingleResult(); if
		 * (existingRole.getRolename().equals(role.getRolename())) {
		 *  user.setRole(existingRole); }
		 */
		em.persist(user);
		em.getTransaction().commit();

	}

	// find user by name
	public User viewUserByEmail(String email) {
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u where u.email=:nameparam ", User.class);
		query.setParameter("nameparam", email);
		User user = query.getSingleResult();
		System.out.println(user);
		return user;
		// return true;

	}

	// view all users
	public List<User> viewAllUsers() {
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
		List<User> users = query.getResultList();
		System.out.println(users);
		return users;
	}

	// deleting user by id
	public void deleteUser(int id, String email) {
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u where u.email=:emailparam ", User.class);
		query.setParameter("emailparam", email);
		User users = query.getSingleResult();
		em.getTransaction().begin();
		User foundUser = em.find(User.class, id);
		em.remove(foundUser);
		em.getTransaction().commit();
	}

	// to make changes in existing user
	public void editUser(User foundUser) {
		em.getTransaction().begin();
		em.merge(foundUser);
		em.getTransaction().commit();

	}
/*....................................User Methods not allowed to Admin.........................................*/

	// adding photos and videos
	public Content addContent(Content content) {
		em.getTransaction().begin();
		em.persist(content);
		em.getTransaction().commit();
		return content;

	}

	// deleting particular photos and videos
	public void deleteContent(int ContentId, String Caption) {
		TypedQuery<Content> query = em.createQuery("SELECT c FROM Content c where c.caption=:fileparam",Content.class);
		query.setParameter("fileparam", Caption);
		Content content = query.getSingleResult();
		em.getTransaction().begin();
		Content foundContent = em.find(Content.class, ContentId);
		em.remove(foundContent);
		em.getTransaction().commit();
	}

	// making changes in the content
	public void editContent(Content foundContent) {
		em.getTransaction().begin();
		em.merge(foundContent);
		em.getTransaction().commit();
		System.out.println(foundContent);
	}

	// to view a particular picture or video
	public Content viewContentByCaption(String Caption) {
		TypedQuery<Content> query = em.createQuery("SELECT c FROM Content c where c.caption=:fileparam",Content.class);
		query.setParameter("fileparam", Caption);
		Content content = query.getSingleResult();
		System.out.println(content);
		return content;
	}

	// to view whole content
	public List<Content> viewAllContents() {
		TypedQuery<Content> query = em.createQuery("SELECT c FROM Content c", Content.class);
		List<Content> contents = query.getResultList();
		System.out.println(contents);
		return contents;
	}

}
