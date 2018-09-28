package bookstore.dao.hibernate;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import bookstore.annotation.Log;
import bookstore.dao.CustomerDAO;
import bookstore.pbean.TCustomer;

@Repository("CustomerDAOImplBId")
public class CustomerDAOImpl<T extends SessionFactory> /*extends HibernateDaoSupport*/ implements CustomerDAO<T> {

	@Log private static Logger log;

	public int getCustomerNumberByUid(final T sessionFactory, final String inUid) {
		log.log(Level.INFO, "inUid={0}", new Object[] { inUid });

		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		return ht.execute(session -> {
			Query numQuery = session
					.createQuery("select count(*) from TCustomer customer where customer.username like :USERNAME");
			numQuery.setString("USERNAME", inUid);
			return (Long)numQuery.uniqueResult();
		}).intValue();
	}

	public TCustomer findCustomerByUid(final T sessionFactory, final String inUid) {
		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		return ht.execute(session -> {
			Query priceQuery = session
					.createQuery("from TCustomer customer where customer.username like :USERNAME");
			priceQuery.setString("USERNAME", inUid);
			return (TCustomer)priceQuery.uniqueResult();
		});
	}

	public void saveCustomer(final T sessionFactory, String inUid, String inPasswordMD5, String inName, String inEmail) {
		TCustomer saveCustomer = new TCustomer();

		saveCustomer.setUsername(inUid);
		saveCustomer.setPasswordmd5(inPasswordMD5);
		saveCustomer.setName(inName);
		saveCustomer.setEmail(inEmail);

		new HibernateTemplate(sessionFactory).save(saveCustomer);
	}
}
