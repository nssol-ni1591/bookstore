package bookstore.service.spring;

import java.sql.SQLException;
import java.util.List;
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
import bookstore.service.AbstractOrderService;
import bookstore.vbean.VOrder;

@UsedSpring
@Component("ServiceOrderImplBId")
public class OrderServiceWrapper extends AbstractOrderService<SessionFactory> {

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
		// springのDIはsingletonらしいので、ここでnullを指定して、
		// DAO実装クラス側でDIしたsessionFactoryを使用してもTx動作に違いはない。らしい
		//return sessionFactory
		return null;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	// このクラスではコンテキストxmlにTransactionAttributesを定義していないので@Transactionalが必要
	public void orderBooks(String inUid, List<String> inISBNs) throws Exception {
		//rollbackするための例外はRuntimeExceptionでないといけない
		try {
			super.orderBooks(inUid, inISBNs);
		}
		catch (RuntimeException e) {
			throw e;
		}
		catch (Exception e) {
			throw new SpringRuntimeException(e);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public List<VOrder> listOrders(List<String> orderIdList) throws SQLException {
		return super.listOrders(orderIdList);
	}

}
