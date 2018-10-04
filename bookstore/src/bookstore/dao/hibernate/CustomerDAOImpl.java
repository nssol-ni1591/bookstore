package bookstore.dao.hibernate;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import bookstore.annotation.Log;
import bookstore.dao.CustomerDAO;
import bookstore.pbean.TCustomer;

@Repository("CustomerDAOImplBId")
public class CustomerDAOImpl<T extends SessionFactory> /*extends HibernateDaoSupport*/ implements CustomerDAO<T> {

	// @Autowiredでもコンテキストxmlでも、少なくとも一連の処理では同じインスタンスが設定されていた
	private SessionFactory sessionFactory3;
	//@Autowired SessionFactory sessionFactory3
	@Log private static Logger log;

	public int getCustomerNumberByUid(final T sessionFactory2, final String inUid) {
		SessionFactory sessionFactory = sessionFactory2 != null ? sessionFactory2 : sessionFactory3;

		log.log(Level.INFO, "inUid={0}", new Object[] { inUid });

		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		return ht.execute(session -> {
			Query numQuery = session
					.createQuery("select count(*) from TCustomer customer where customer.username like :USERNAME");
			numQuery.setString("USERNAME", inUid);
			return (Long)numQuery.uniqueResult();
		}).intValue();
	}

	public TCustomer findCustomerByUid(final T sessionFactory2, final String inUid) {
		SessionFactory sessionFactory = sessionFactory2 != null ? sessionFactory2 : sessionFactory3;

		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		return ht.execute(session -> {
			Query priceQuery = session
					.createQuery("from TCustomer customer where customer.username like :USERNAME");
			priceQuery.setString("USERNAME", inUid);
			return (TCustomer)priceQuery.uniqueResult();
		});
	}

	public void saveCustomer(final T sessionFactory2, String inUid, String inPasswordMD5, String inName, String inEmail) {
		SessionFactory sessionFactory = sessionFactory2 != null ? sessionFactory2 : sessionFactory3;

		TCustomer saveCustomer = new TCustomer();
		saveCustomer.setUsername(inUid);
		saveCustomer.setPasswordmd5(inPasswordMD5);
		saveCustomer.setName(inName);
		saveCustomer.setEmail(inEmail);
		new HibernateTemplate(sessionFactory).save(saveCustomer);
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory3 = sessionFactory;
	}

}
