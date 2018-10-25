package bookstore.dao.jpa;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import bookstore.dao.CustomerDAO;
import bookstore.pbean.TCustomer;

public abstract class AbstractCustomerDAOImpl<T extends EntityManager> implements CustomerDAO<T> {

	/*
	 * transaction-type‚ªJTA‚©RESOURCE_LOCAL‚Æ‚Ìˆá‚¢‚ÍA
	 * EntityManager‚Ìæ“¾•û–@‚Ìˆá‚¢‚¾‚¯
	 */
	protected abstract EntityManager getEntityManager();
	protected abstract Logger getLogger();


	@Override
	public int getCustomerNumberByUid(final T em2, String uid) {
		EntityManager em = em2 != null ? em2 : getEntityManager();

		Query q = em
				.createQuery("select c from TCustomer c where c.username=:username");
		q.setParameter("username", uid);
		return q.getResultList().size();
	}

	@Override
	public TCustomer findCustomerByUid(final T em2, String uid) {
		EntityManager em = em2 != null ? em2 : getEntityManager();
		getLogger().log(Level.INFO, "em={0}", em);

		Query q = em
				.createQuery("select c from TCustomer c where c.username=:username");
		q.setParameter("username", uid);
		return (TCustomer) q.getSingleResult();
	}

	@Override
	public void saveCustomer(final T em2, String username, String passwordMD5, String name, String email) {
		EntityManager em = em2 != null ? em2 : getEntityManager();
		getLogger().log(Level.INFO, "entityManager={0}", em);

		em.getTransaction().begin();
		TCustomer customer = new TCustomer();
		customer.setUsername(username);
		customer.setPasswordmd5(passwordMD5);
		customer.setName(name);
		customer.setEmail(email);
		em.persist(customer);

		em.getTransaction().commit();
	}

}
