package bookstore.dao.hibernate;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import bookstore.annotation.Log;
import bookstore.dao.OrderDAO;
import bookstore.pbean.TCustomer;
import bookstore.pbean.TOrder;

@Repository("OrderDAOImplBId")
public class OrderDAOImpl<T extends SessionFactory> /*extends HibernateDaoSupport*/ implements OrderDAO<T> {

	@Log private static Logger log;

	public TOrder createOrder(final T sessionFactory, TCustomer inCustomer) {
		log.log(Level.INFO, "sessionFactory={0}", sessionFactory);

		TOrder saveOrder = new TOrder();
		saveOrder.setTCustomer(inCustomer);
		saveOrder.setOrderday(Timestamp.valueOf(LocalDateTime.now()));

		new HibernateTemplate(sessionFactory).save(saveOrder);

		log.log(Level.INFO, "customer_id={0}, order_id={1}"
				, new Object[] { inCustomer.getId(), saveOrder.getId() });
		return (saveOrder);
	}

	public List<TOrder> retrieveOrders(final T sessionFactory, final List<String> orderIdList) {
		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		if (orderIdList == null) {
			@SuppressWarnings("unchecked")
			List<TOrder> list = ht.find("from TOrder order");
			return list;
		}
		else {
			return ht.execute(session -> {
				Query retrieveQuery = session.createQuery("from TOrder order where order.id in ( :ID )");
				retrieveQuery.setParameterList("ID", orderIdList);
				@SuppressWarnings("unchecked")
				List<TOrder> list = retrieveQuery.list();
				return list;
			});
		}
	}
}
