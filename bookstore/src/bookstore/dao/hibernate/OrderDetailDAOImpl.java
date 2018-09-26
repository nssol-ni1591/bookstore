package bookstore.dao.hibernate;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import bookstore.dao.OrderDetailDAO;
import bookstore.pbean.TBook;
import bookstore.pbean.TOrder;
import bookstore.pbean.TOrderDetail;

@Repository("OrderDetailDAOImplBId")
public class OrderDetailDAOImpl extends HibernateDaoSupport implements OrderDetailDAO {

	public void createOrderDetail(TOrder inOrder, TBook inBook) {
		if ("0-0000-0000-0".equals(inBook.getIsbn())) {
			throw new IllegalArgumentException("isdn: 0-0000-0000-0");
		}

		TOrderDetail saveOrderDetail = new TOrderDetail();
		saveOrderDetail.setTOrder(inOrder);
		saveOrderDetail.setTBook(inBook);
		getHibernateTemplate().save(saveOrderDetail);
	}
}
