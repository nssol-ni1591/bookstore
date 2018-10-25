package bookstore.dao.jpa;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import bookstore.dao.OrderDetailDAO;
import bookstore.pbean.TBook;
import bookstore.pbean.TOrder;
import bookstore.pbean.TOrderDetail;

public abstract class AbstractOrderDetailDAOImpl<T extends EntityManager> implements OrderDetailDAO<T> {

	/*
	 * transaction-type‚ªJTA‚©RESOURCE_LOCAL‚Æ‚Ìˆá‚¢‚ÍA
	 * EntityManager‚Ìæ“¾•û–@‚Ìˆá‚¢‚¾‚¯
	 */
	protected abstract EntityManager getEntityManager();
	protected abstract Logger getLogger();


	@Override
	public void createOrderDetail(final T em2, TOrder order, TBook book) throws SQLException {
		EntityManager em = em2 != null ? em2 : getEntityManager();
		getLogger().log(Level.INFO, "order_id={0}, book_id={1}"
				, new Object[] { order.getId(), book.getId() });

		if ("0-0000-0000-0".equals(book.getIsbn())) {
			order.setId(0);
		}

		TOrderDetail orderDetail = new TOrderDetail();
		orderDetail.setTOrder(order);
		orderDetail.setTBook(book);
		em.persist(orderDetail);
	}

	@Override
	public List<TOrderDetail> listOrderDetails(final T em2, List<String> orders) {
		EntityManager em = em2 != null ? em2 : getEntityManager();

		Query query = em.createQuery("select d from TOrderDetail d");
		@SuppressWarnings("unchecked")
		List<TOrderDetail> details = query.getResultList();
	    return details;
	}

}
