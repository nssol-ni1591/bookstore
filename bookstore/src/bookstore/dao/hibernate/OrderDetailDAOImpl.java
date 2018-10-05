package bookstore.dao.hibernate;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import bookstore.annotation.Log;
import bookstore.dao.OrderDetailDAO;
import bookstore.pbean.TBook;
import bookstore.pbean.TOrder;
import bookstore.pbean.TOrderDetail;

@Repository("OrderDetailDAOImplBId")
public class OrderDetailDAOImpl<T extends SessionFactory> /*extends HibernateDaoSupport*/ implements OrderDetailDAO<T> {

	// @Autowiredでもコンテキストxmlでも、少なくとも一連の処理では同じインスタンスが設定される
	private SessionFactory sessionFactory3;
	//@Autowired SessionFactory sessionFactory3
	@Log private static Logger log;

	public void createOrderDetail(final T sessionFactory2, TOrder inOrder, TBook inBook) throws SQLException {
		SessionFactory sessionFactory = sessionFactory2 != null ? sessionFactory2 : sessionFactory3;

		log.log(Level.INFO, "sessionFactory={0}", sessionFactory);
		log.log(Level.INFO, "order_id={0}, book_id={1}"
				, new Object[] {
						 inOrder == null ? "null" : inOrder.getId()
						, inBook == null ? "null" : inBook.getId()
		});

		if (inBook == null || "0-0000-0000-0".equals(inBook.getIsbn())) {
			throw new SQLException("isdn: 0-0000-0000-0");
		}

		TOrderDetail saveOrderDetail = new TOrderDetail();
		saveOrderDetail.setTOrder(inOrder);
		saveOrderDetail.setTBook(inBook);
		new HibernateTemplate(sessionFactory).save(saveOrderDetail);
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory3 = sessionFactory;
	}

}
