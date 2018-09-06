package bookstore.dao.eclipselink;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import bookstore.annotation.UsedEclipselink;
import bookstore.dao.OrderDetailDAO;
import bookstore.pbean.TBook;
import bookstore.pbean.TOrder;
import bookstore.pbean.TOrderDetail;

@UsedEclipselink
public class OrderDetailDAOImpl implements OrderDetailDAO {

	@PersistenceContext(unitName = "BookStore")
	//private EntityManager em;
	private EntityManager em = Persistence.createEntityManagerFactory("BookStore").createEntityManager();

	@Override
	public void createOrderDetail(TOrder inOrder, TBook inBook) {
		TOrderDetail orderDetail = new TOrderDetail();
		orderDetail.setTOrder(inOrder);
		orderDetail.setTBook(inBook);
		em.persist(orderDetail);
	}

}
