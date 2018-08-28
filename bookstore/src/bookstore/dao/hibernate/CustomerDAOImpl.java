package bookstore.dao.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import bookstore.dao.CustomerDAO;
import bookstore.pbean.TCustomer;

public class CustomerDAOImpl extends HibernateDaoSupport implements CustomerDAO {

	public int getCustomerNumberByUid(final String inUid) {

		HibernateTemplate ht = getHibernateTemplate();

		return (((Long) ht.execute(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException {

				Query numQuery = session
						.createQuery("select count(*) from TCustomer customer where customer.username like :USERNAME");
				numQuery.setString("USERNAME", inUid);

				return ((Long) numQuery.uniqueResult());
			}
		})).intValue());
	}

	public TCustomer findCustomerByUid(final String inUid) {

		HibernateTemplate ht = getHibernateTemplate();

		return (((TCustomer) ht.execute(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException {

				Query priceQuery = session
						.createQuery("from TCustomer customer where customer.username like :USERNAME");
				priceQuery.setString("USERNAME", inUid);

				return ((TCustomer) priceQuery.uniqueResult());
			}
		})));
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