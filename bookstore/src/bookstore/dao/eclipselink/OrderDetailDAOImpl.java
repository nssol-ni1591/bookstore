package bookstore.dao.eclipselink;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import bookstore.dao.OrderDetailDAO;
import bookstore.pbean.TBook;
import bookstore.pbean.TOrder;
import bookstore.pbean.TOrderDetail;

public class OrderDetailDAOImpl implements OrderDetailDAO {

	@PersistenceContext(unitName = "BookStore")
	private EntityManager em;

	@Override
	public void createOrderDetail(TOrder inOrder, TBook inBook) {
		TOrderDetail orderDetail = new TOrderDetail();
		orderDetail.setTOrder(inOrder);
		orderDetail.setTBook(inBook);
		em.persist(orderDetail);
	}

}
