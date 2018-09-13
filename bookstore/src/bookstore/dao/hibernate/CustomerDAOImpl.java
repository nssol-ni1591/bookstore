package bookstore.dao.hibernate;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import bookstore.annotation.Log;
import bookstore.dao.CustomerDAO;
import bookstore.pbean.TCustomer;

@Repository("CustomerDAOImplBId")
public class CustomerDAOImpl extends HibernateDaoSupport implements CustomerDAO {

	@Log private static Logger log;

	@Autowired @Qualifier("sessionFactory") SessionFactory sessionFactory;

	public int getCustomerNumberByUid(final String inUid) {
		log.log(Level.INFO, "inUid={0}, sessionFactory={1}", new Object[] { inUid, sessionFactory.getClass().getName() });

		HibernateTemplate ht = getHibernateTemplate();
		return ht.execute(session -> {
			Query numQuery = session
					.createQuery("select count(*) from TCustomer customer where customer.username like :USERNAME");
			numQuery.setString("USERNAME", inUid);
			return (Long)numQuery.uniqueResult();
		}).intValue();
	}

	public TCustomer findCustomerByUid(final String inUid) {

		HibernateTemplate ht = getHibernateTemplate();

		return ht.execute(session -> {
			Query priceQuery = session
					.createQuery("from TCustomer customer where customer.username like :USERNAME");
			priceQuery.setString("USERNAME", inUid);
			return (TCustomer)priceQuery.uniqueResult();
		});
	}

	public void saveCustomer(String inUid, String inPasswordMD5, String inName,
			String inEmail) {
		TCustomer saveCustomer = new TCustomer();

		saveCustomer.setUsername(inUid);
		saveCustomer.setPasswordmd5(inPasswordMD5);
		saveCustomer.setName(inName);
		saveCustomer.setEmail(inEmail);

		getHibernateTemplate().save(saveCustomer);
	}
}
