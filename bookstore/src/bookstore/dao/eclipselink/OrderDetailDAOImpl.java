package bookstore.dao.eclipselink;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import bookstore.annotation.UsedEclipselink;
import bookstore.dao.OrderDetailDAO;
import bookstore.pbean.TBook;
import bookstore.pbean.TOrder;
import bookstore.pbean.TOrderDetail;

@UsedEclipselink
@Dependent
public class OrderDetailDAOImpl<T extends EntityManager> implements OrderDetailDAO<T> {

	//Tomcatでは@PersistenceContextは使えない
	//@PersistenceContext(unitName = "BookStore") private EntityManager em;
	@PersistenceUnit(name = "BookStore") private EntityManagerFactory emf;
	//private EntityManager em = Persistence.createEntityManagerFactory("BookStore").createEntityManager()
	//@Inject private EntityManager em;
	@Inject private Logger log;

	@Override
	public void createOrderDetail(final T em2, TOrder inOrder, TBook inBook) throws SQLException {
		EntityManager em = em2 != null ? em2 : emf.createEntityManager();
		log.log(Level.INFO, "order_id={0}, book_id={1}"
				, new Object[] { inOrder.getId(), inBook.getId() });

		if ("0-0000-0000-0".equals(inBook.getIsbn())) {
			throw new SQLException("isdn: 0-0000-0000-0");
		}

		TOrderDetail orderDetail = new TOrderDetail();
		orderDetail.setTOrder(inOrder);
		orderDetail.setTBook(inBook);
		em.persist(orderDetail);
		//return orderDetail;
	}

	@Override
	public List<TOrderDetail> listOrderDetails(final T em2, List<String> orders) {
		EntityManager em = em2 != null ? em2 : emf.createEntityManager();
		Query query = em.createQuery("select d from TOrderDetail d");
		@SuppressWarnings("unchecked")
		List<TOrderDetail> details = query.getResultList();
	    return details;
	}

}
