package bookstore.dao.eclipselink;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import bookstore.annotation.UsedEclipselink;
import bookstore.dao.CustomerDAO;
import bookstore.pbean.TCustomer;

@UsedEclipselink
@Dependent
public class CustomerDAOImpl implements CustomerDAO {

	//Tomcatでは@PersistenceContextは使えない
	//@PersistenceContext(unitName = "BookStore") private EntityManager em
	//private EntityManager em = Persistence.createEntityManagerFactory("BookStore").createEntityManager()
	@Inject private EntityManager em;
	@Inject private Logger log;

	@Override
	public int getCustomerNumberByUid(String inUid) {
		Query q = em
				.createQuery("select c from TCustomer c where c.username=:username");
		q.setParameter("username", inUid);
		return q.getResultList().size();
	}

	@Override
	public TCustomer findCustomerByUid(String inUid) {
		log.log(Level.FINE, "inUid={0}", inUid);
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
