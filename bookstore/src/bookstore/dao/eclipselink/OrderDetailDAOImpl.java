package bookstore.dao.eclipselink;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import bookstore.annotation.UsedEclipselink;
import bookstore.dao.OrderDetailDAO;
import bookstore.pbean.TBook;
import bookstore.pbean.TOrder;
import bookstore.pbean.TOrderDetail;

@UsedEclipselink
public class OrderDetailDAOImpl implements OrderDetailDAO {

	//Tomcat‚Å‚Í@PersistenceContext‚ÍŽg‚¦‚È‚¢
	//@PersistenceContext(unitName = "BookStore") private EntityManager em;
	//private EntityManager em = Persistence.createEntityManagerFactory("BookStore").createEntityManager();
	@Inject private EntityManager em;

	@Override
	public void createOrderDetail(TOrder inOrder, TBook inBook) {
		TOrderDetail orderDetail = new TOrderDetail();
		orderDetail.setTOrder(inOrder);
		orderDetail.setTBook(inBook);
		em.persist(orderDetail);
	}

}
