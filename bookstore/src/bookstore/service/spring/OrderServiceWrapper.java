package bookstore.service.spring;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import bookstore.annotation.Log;
import bookstore.annotation.UsedSpring;
import bookstore.dao.BookDAO;
import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.dao.OrderDetailDAO;
import bookstore.service.AbstractOrderService;

@UsedSpring
@Component("ServiceOrderImplBId")
public class OrderServiceWrapper extends AbstractOrderService<SessionFactory> {

	@Log private static Logger log;

	private BookDAO<SessionFactory> bookdao;
	private CustomerDAO<SessionFactory> customerdao;
	private OrderDAO<SessionFactory> orderdao;
	private OrderDetailDAO<SessionFactory> odetaildao;
	//private SessionFactory sessionFactory

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
		// ここでnullを指定してDAO実装クラス側でDIしたsessionFactoryを使用しても
		// Tx動作に違いはない。らしい
		//return sessionFactory
		return null;
	}

	// DIを実現するためのSetterメソッドs
	public void setBookdao(BookDAO<SessionFactory> bookdao) {
		this.bookdao = bookdao;
	}
	public void setCustomerdao(CustomerDAO<SessionFactory> customerdao) {
		this.customerdao = customerdao;
	}
	public void setOrderdao(OrderDAO<SessionFactory> orderdao) {
		this.orderdao = orderdao;
	}
	public void setOrderdetaildao(OrderDetailDAO<SessionFactory> odetaildao) {
		this.odetaildao = odetaildao;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		//this.sessionFactory = sessionFactory
	}

	// コンテキスト.xmlにtransactionAttributesを定義しているので@Transactionalを省略できる
	@Override
	public void orderBooks(String uid, List<String> inISBNs) throws Exception {
		//rollbackするための例外はRuntimeExceptionでないといけない
		try {
			super.orderBooks(uid, inISBNs);
		}
		catch (RuntimeException e) {
			throw e;
		}
		catch (Exception e) {
			throw new SpringRuntimeException(e);
		}
	}

}
