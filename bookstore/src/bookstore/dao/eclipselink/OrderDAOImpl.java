package bookstore.dao.eclipselink;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import bookstore.annotation.UsedEclipselink;
import bookstore.dao.OrderDAO;
import bookstore.pbean.TCustomer;
import bookstore.pbean.TOrder;

@UsedEclipselink
@Dependent
public class OrderDAOImpl implements OrderDAO {

	//Tomcat‚Å‚Í@PersistenceContext‚ÍŽg‚¦‚È‚¢
	@PersistenceContext(unitName = "BookStore") private EntityManager em;
	//private EntityManager em = Persistence.createEntityManagerFactory("BookStore").createEntityManager()
	//@Inject private EntityManager em;
	@Inject private Logger log;

	@Override
	public List<TOrder> retrieveOrders(List<String> orderIdList) {
		Query q;
		if (orderIdList == null) {
			q = em.createQuery("select o from TOrder o");
			@SuppressWarnings("unchecked")
			List<TOrder> resultList = q.getResultList();
			return resultList;
		}

		q = em.createQuery("select o from TOrder o where o.id in ( :ID )");
		q.setParameter("ID", orderIdList);
		@SuppressWarnings("unchecked")
		List<TOrder> orders = q.getResultList();
		return orders;
	}

	@Override
	public TOrder createOrder(TCustomer inCustomer) {
		TOrder order = new TOrder();
		order.setOrderday(Timestamp.valueOf(LocalDateTime.now()));
		order.setTCustomer(inCustomer);
		em.persist(order);

		log.log(Level.INFO, "customer_id={0}, order_id={1}"
				, new Object[] { inCustomer.getId(), order.getId() });
		return order;
	}

}
