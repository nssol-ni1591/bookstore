package bookstore.dao.hibernate;

import java.util.List;
import java.util.Calendar;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import bookstore.dao.OrderDAO;
import bookstore.pbean.TCustomer;
import bookstore.pbean.TOrder;

public class OrderDAOImpl extends HibernateDaoSupport implements OrderDAO {
	public TOrder createOrder(TCustomer inCustomer) {

		TOrder saveOrder = new TOrder();
		saveOrder.setTCustomer(inCustomer);
		saveOrder.setOrderday(Calendar.getInstance().getTime());

		getHibernateTemplate().save(saveOrder);

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
