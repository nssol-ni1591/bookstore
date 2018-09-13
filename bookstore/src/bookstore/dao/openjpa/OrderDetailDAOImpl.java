package bookstore.dao.openjpa;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import bookstore.annotation.UsedOpenJpa;
import bookstore.dao.OrderDetailDAO;
import bookstore.pbean.TBook;
import bookstore.pbean.TOrder;
import bookstore.pbean.TOrderDetail;

@UsedOpenJpa
@Dependent
public class OrderDetailDAOImpl implements OrderDetailDAO {

	//Tomcat‚Å‚Í@PersistenceContext‚ÍŽg‚¦‚È‚¢
	@PersistenceContext(unitName = "BookStore") private EntityManager em;
	//private EntityManager em = Persistence.createEntityManagerFactory("BookStore").createEntityManager()
	//@Inject private EntityManager em

	@Override
	public void createOrderDetail(TOrder inOrder, TBook inBook) {
		TOrderDetail orderDetail = new TOrderDetail();
		orderDetail.setTOrder(inOrder);
		orderDetail.setTBook(inBook);
		em.persist(orderDetail);
	}

}
