package bookstore.dao.eclipselink;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import bookstore.annotation.UsedEclipselink;
import bookstore.dao.CustomerDAO;
import bookstore.pbean.TCustomer;

@UsedEclipselink
public class CustomerDAOImpl implements CustomerDAO {

	//Tomcat‚Å‚Í@PersistenceContext‚ÍŽg‚¦‚È‚¢
	//@PersistenceContext(unitName = "BookStore") private EntityManager em;
	//private EntityManager em = Persistence.createEntityManagerFactory("BookStore").createEntityManager();
	@Inject private EntityManager em;

	private static final boolean DEBUG = false;

	@Override
	public int getCustomerNumberByUid(String inUid) {
		Query q = em
				.createQuery("select c from TCustomer c where c.username=:username");
		q.setParameter("username", inUid);
		return q.getResultList().size();
	}

	@Override
	public TCustomer findCustomerByUid(String inUid) {
		if (DEBUG)
			System.out.println("CustomerDAOImpl.findCustomerByUid: inUid=" + inUid);
		Query q = em
				.createQuery("select c from TCustomer c where c.username=:username");
		q.setParameter("username", inUid);
		return (TCustomer) q.getSingleResult();
	}

	@Override
	public void saveCustomer(String inUid, String inPasswordMD5, String inName,
			String inEmail) {
		TCustomer customer = new TCustomer();
		customer.setUsername(inUid);
		customer.setPasswordmd5(inPasswordMD5);
		customer.setName(inName);
		customer.setEmail(inEmail);
		em.persist(customer);
	}

}
