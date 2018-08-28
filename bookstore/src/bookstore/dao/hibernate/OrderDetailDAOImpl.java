package bookstore.dao.hibernate;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import bookstore.dao.OrderDetailDAO;
import bookstore.pbean.TBook;
import bookstore.pbean.TOrder;
import bookstore.pbean.TOrderDetail;

public class OrderDetailDAOImpl extends HibernateDaoSupport implements OrderDetailDAO{
	public void createOrderDetail(TOrder inOrder, TBook inBook) {

		TOrderDetail saveOrderDetail = new TOrderDetail();

		saveOrderDetail.setTOrder( inOrder );
		saveOrderDetail.setTBook( inBook );
		getHibernateTemplate().save( saveOrderDetail );
	}
}