package pixogram;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pixogram.sprint1.Comments;

public class TestComments {
		private EntityManager em;
		@Before
		public void setUp() {
			/* Create EntityManagerFactory */
			EntityManagerFactory emf = Persistence
					.createEntityManagerFactory("test");
			/*Create EntityManager*/
			em = emf.createEntityManager();
		}
//		@Test
		public void testAddComments() {
			try {
		    Comments comment1= new Comments("This is a test comment1.");
		    Comments comment2= new Comments("This is a test comment2.");
//		    Comments comment3= new Comments(123);
			em.getTransaction().begin();
			em.persist(comment1);
			em.persist(comment2);
//			em.persist(comment3);
			}
			catch(Exception e){
			e.printStackTrace();
			em.getTransaction().rollback();
			}
			em.getTransaction().commit();
		}
		
	@Test
		public void testFindCommentsById() {
			TypedQuery<Comments> query = em.createQuery("SELECT u FROM Comments u where u.commentId=:idparam", Comments.class);
			query.setParameter("idparam", 2);
			List<Comments> comments = query.getSingleResult();
			System.out.println(comments);
		}
	
//	@Test
	public void viewAllComments() {
		TypedQuery<Comments> query = em.createQuery("SELECT u FROM Comments u", Comments.class);
		List<Comments> comments = query.getResultList();
		System.out.println(comments);
	}
	
	@After
	public void destroy() {
		em.close();
	}
		
		
}