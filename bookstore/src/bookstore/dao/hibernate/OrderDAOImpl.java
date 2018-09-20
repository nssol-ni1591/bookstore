package bookstore.dao.hibernate;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import bookstore.annotation.Log;
import bookstore.dao.OrderDAO;
import bookstore.pbean.TCustomer;
import bookstore.pbean.TOrder;

@Repository("OrderDAOImplBId")
public class OrderDAOImpl extends HibernateDaoSupport implements OrderDAO {

	@Autowired @Qualifier("sessionFactory") SessionFactory sessionFactory;
	@Log Logger log;

	public TOrder createOrder(TCustomer inCustomer) {

		TOrder saveOrder = new TOrder();
		saveOrder.setTCustomer(inCustomer);
		saveOrder.setOrderday(Timestamp.valueOf(LocalDateTime.now()));

		getHibernateTemplate().save(saveOrder);

		log.log(Level.INFO, "customer_id={0}, order_id={1}"
				, new Object[] { inCustomer.getId(), saveOrder.getId() });
		return (saveOrder);
	}

	@SuppressWarnings("unchecked")
	public List<TOrder> retrieveOrders(final List<String> orderIdList) {

		HibernateTemplate ht = getHibernateTemplate();

		if (orderIdList == null) {
			return ht.find("from TOrder order");
		}
		else {
			return ht.execute(session -> {
				Query retrieveQuery = session.createQuery("from TOrder order where order.id in ( :ID )");
				retrieveQuery.setParameterList("ID", orderIdList);
				return retrieveQuery.list();
			});
		}
	}
}
