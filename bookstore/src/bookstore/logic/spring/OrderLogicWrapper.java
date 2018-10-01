package bookstore.logic.spring;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import bookstore.annotation.Log;
import bookstore.annotation.UsedSpring;
import bookstore.dao.BookDAO;
import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.dao.OrderDetailDAO;
import bookstore.logic.AbstractOrderLogic;
import bookstore.vbean.VOrder;

@UsedSpring
@Component("LogicOrderImplBId")
public class OrderLogicWrapper extends AbstractOrderLogic<SessionFactory> {

	@Log private static Logger log;

	@Autowired @Qualifier("BookDAOBId") BookDAO<SessionFactory> bookdao;
	@Autowired @Qualifier("CustomerDAOBId") CustomerDAO<SessionFactory> customerdao;
	@Autowired @Qualifier("OrderDAOBId") OrderDAO<SessionFactory> orderdao;
	@Autowired @Qualifier("OrderDetailDAOBId") OrderDetailDAO<SessionFactory> odetaildao;
	@Autowired @Qualifier("sessionFactory") SessionFactory sessionFactory;

	@Override
	protected BookDAO<SessionFactory> getBookDAO() {
		return bookdao;
	}
	@Override
	protected CustomerDAO<SessionFactory> getCustomerDAO() {
		return customerdao;
	}
	@Override
	protected OrderDAO<SessionFactory> getOrderDAO() {
		return orderdao;
	}
	@Override
	protected OrderDetailDAO<SessionFactory> getOrderDetailDAO() {
		return odetaildao;
	}
	@Override
	protected Logger getLogger() {
		return log;
	}
	@Override
	protected SessionFactory getManager() {
		return sessionFactory;
	}
/*
	public void setBookdao(BookDAO<Object> bookdao) {
		this.bookdao = bookdao;
	}
	public void setCustomerdao(CustomerDAO<Object> customerdao) {
		this.customerdao = customerdao;
	}
	public void setOrderdao(OrderDAO<Object> orderdao) {
		this.orderdao = orderdao;
	}
	public void setOrderdetaildao(OrderDetailDAO<Object> odetaildao) {
		this.odetaildao = odetaildao;
	}
*/

	@Override
	@Transactional(propagation=Propagation.REQUIRED)	//-> applicationContext.xmlÇ…ìØìôÇÃíËã`Ç†ÇËÅH
	public void orderBooks(String inUid, List<String> inISBNs) throws Exception {
		//rollbackÇ∑ÇÈÇΩÇﬂÇÃó·äOÇÕRuntimeExceptionÇ≈Ç»Ç¢Ç∆Ç¢ÇØÇ»Ç¢
		try {
			super.orderBooks(inUid, inISBNs);
		}
		catch (Exception e) {
			log.log(Level.SEVERE, "e={0}", new Object[] { e.getMessage() });
			throw new SpringRuntimeException(e);
		}

		/*
		// Non-managed environment idiom
		log.log(Level.INFO, "sessionFactory={0}", sessionFactory);

		Session sess = sessionFactory.openSession();
		// No Hibernate Session bound to thread, and configuration does not allow creation of non-transactional one here
		//Session sess = sessionFactory.getCurrentSession();

		//Transaction tx = sess.beginTransaction();
		Transaction tx = sess.getTransaction();
		try {
			tx.begin();
			super.orderBooks(inUid, inISBNs);
			tx.commit();
		}
		catch (Exception e) {
			log.log(Level.SEVERE, "e={0}, tx={1}, active={2}", new Object[] { e.getMessage(), tx, tx == null ? "null" : tx.isActive() });
			tx.rollback();
			throw e;
			//throw new RuntimeException(e);
		}
		finally {
			sess.close();
		}
		 */
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public List<VOrder> listOrders(List<String> orderIdList) throws SQLException {
		return super.listOrders(orderIdList);
	}

}
