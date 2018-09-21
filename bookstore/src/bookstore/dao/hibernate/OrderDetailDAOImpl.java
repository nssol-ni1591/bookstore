package bookstore.dao.hibernate;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import bookstore.annotation.Log;
import bookstore.dao.OrderDetailDAO;
import bookstore.pbean.TBook;
import bookstore.pbean.TOrder;
import bookstore.pbean.TOrderDetail;

@Repository("OrderDetailDAOImplBId")
public class OrderDetailDAOImpl<T> extends HibernateDaoSupport implements OrderDetailDAO<T> {

	@Autowired @Qualifier("sessionFactory") SessionFactory sessionFactory;
	@Log private static Logger log;

	public void createOrderDetail(final T em, TOrder inOrder, TBook inBook) throws SQLException {
		log.log(Level.INFO, "order_id={0}, book_id={1}"
				, new Object[] {
						 inOrder == null ? "null" : inOrder.getId()
						, inBook == null ? "null" : inBook.getId()
		});

		if ("0-0000-0000-0".equals(inBook.getIsbn())) {
			throw new SQLException("isdn: 0-0000-0000-0");
		}

		TOrderDetail saveOrderDetail = new TOrderDetail();
		saveOrderDetail.setTOrder(inOrder);
		saveOrderDetail.setTBook(inBook);
		getHibernateTemplate().save(saveOrderDetail);
	}
}
