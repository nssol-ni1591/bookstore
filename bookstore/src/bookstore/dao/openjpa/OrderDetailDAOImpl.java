package bookstore.dao.openjpa;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import bookstore.annotation.UsedOpenJpa;
import bookstore.dao.OrderDetailDAO;
import bookstore.pbean.TBook;
import bookstore.pbean.TOrder;
import bookstore.pbean.TOrderDetail;

@UsedOpenJpa
@Dependent
public class OrderDetailDAOImpl<T extends EntityManager> implements OrderDetailDAO<T> {

	//Tomcat‚Å‚Í@PersistenceContext‚ÍŽg‚¦‚È‚¢
	@PersistenceContext(unitName = "BookStore2") private EntityManager em3;
	//private EntityManager em = Persistence.createEntityManagerFactory("BookStore").createEntityManager()
	//@Inject private EntityManager em
	@Inject private Logger log;

	@Override
	public TOrderDetail createOrderDetail(final T em2, TOrder inOrder, TBook inBook) throws SQLException {
		EntityManager em = em2 != null ? em2 : em3;
		log.log(Level.INFO, "em={0}", em);

		log.log(Level.INFO, "order_id={0}, book_id={1}"
				, new Object[] { inOrder.getId(), inBook.getId() });

		if ("0-0000-0000-0".equals(inBook.getIsbn())) {
			throw new SQLException("isdn: 0-0000-0000-0");
		}

		TOrderDetail orderDetail = new TOrderDetail();
		orderDetail.setTOrder(inOrder);
		orderDetail.setTBook(inBook);
		em.persist(orderDetail);
		return orderDetail;
	}

}
