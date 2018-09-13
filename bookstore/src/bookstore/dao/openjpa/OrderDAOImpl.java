package bookstore.dao.openjpa;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import bookstore.annotation.UsedOpenJpa;
import bookstore.dao.OrderDAO;
import bookstore.pbean.TCustomer;
import bookstore.pbean.TOrder;

@UsedOpenJpa
@Dependent
public class OrderDAOImpl implements OrderDAO {

	//Tomcatでは@PersistenceContextは使えない
	@PersistenceContext(unitName = "BookStore") private EntityManager em;
	//private EntityManager em = Persistence.createEntityManagerFactory("BookStore").createEntityManager()
	//@Inject private EntityManager em

	@Override
	public List<TOrder> retrieveOrders(List<String> orderIdList) {

		javax.persistence.Query q;
		if (orderIdList == null) {
			q = em.createQuery("select o from TOrder o");
			@SuppressWarnings("unchecked")
			List<TOrder> resultList = q.getResultList();
			return resultList;
		}

		q = em.createQuery("select order from TOrder order where order.id in ( :ID )");
		q.setParameter("ID", orderIdList);
		@SuppressWarnings("unchecked")
		List<TOrder> orders = q.getResultList();
		return orders;
	}

	@Override
	public TOrder createOrder(TCustomer inCustomer) {
		TOrder order = new TOrder();
		order.setOrderday(new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));
		order.setTCustomer(inCustomer);
		em.persist(order);
		return order;
	}

}
