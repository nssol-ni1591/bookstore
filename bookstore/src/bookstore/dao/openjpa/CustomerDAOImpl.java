package bookstore.dao.openjpa;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import bookstore.annotation.UsedOpenJpa;
import bookstore.dao.CustomerDAO;
import bookstore.pbean.TCustomer;

@UsedOpenJpa
@Dependent
public class CustomerDAOImpl<T extends EntityManager> implements CustomerDAO<T> {

	//Tomcat‚Å‚Í@PersistenceContext‚ÍŽg‚¦‚È‚¢
	@PersistenceContext(unitName = "BookStore2") private EntityManager em3;
	//private EntityManager em = Persistence.createEntityManagerFactory("BookStore").createEntityManager()
	//@Inject private EntityManager em
	@Inject private Logger log;

	@Override
	public int getCustomerNumberByUid(final T em2, String inUid) {
		EntityManager em = em2 != null ? em2 : em3;
		log.log(Level.INFO, "inUid={0}, em={1}", new Object[] { inUid, em.getClass().getName() });
		Query q = em
				.createQuery("select c from TCustomer c where c.username=:username");
		q.setParameter("username", inUid);
		return q.getResultList().size();
	}

	@Override
	public TCustomer findCustomerByUid(final T em2, String inUid) {
		log.log(Level.FINE, "inUid={0}", inUid);
		EntityManager em = em2 != null ? em2 : em3;
		Query q = em
				.createQuery("select c from TCustomer c where c.username=:username");
		q.setParameter("username", inUid);
		return (TCustomer) q.getSingleResult();
	}

	@Override
	public void saveCustomer(final T em2, String inUid, String inPasswordMD5, String inName,
			String inEmail) {
		EntityManager em = em2 != null ? em2 : em3;
		TCustomer customer = new TCustomer();
		customer.setUsername(inUid);
		customer.setPasswordmd5(inPasswordMD5);
		customer.setName(inName);
		customer.setEmail(inEmail);
		em.persist(customer);
	}

}
