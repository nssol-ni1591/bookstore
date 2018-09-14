package bookstore.dao.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import bookstore.dao.OrderDetailDAO;
import bookstore.pbean.TBook;
import bookstore.pbean.TOrder;
import bookstore.pbean.TOrderDetail;

@Repository("OrderDetailDAOImplBId")
public class OrderDetailDAOImpl extends HibernateDaoSupport implements OrderDetailDAO {

	@Autowired @Qualifier("sessionFactory") SessionFactory sessionFactory;

	public TOrderDetail createOrderDetail(TOrder inOrder, TBook inBook) {
		TOrderDetail saveOrderDetail = new TOrderDetail();
		saveOrderDetail.setTOrder(inOrder);
		saveOrderDetail.setTBook(inBook);
		getHibernateTemplate().save(saveOrderDetail);
		return saveOrderDetail;
	}
}
