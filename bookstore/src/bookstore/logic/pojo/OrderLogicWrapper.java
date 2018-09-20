package bookstore.logic.pojo;

import java.util.logging.Logger;

import bookstore.annotation.UsedPojo;
import bookstore.dao.BookDAO;
import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.dao.OrderDetailDAO;
import bookstore.dao.jdbc.BookDAOImpl;
import bookstore.dao.jdbc.CustomerDAOImpl;
import bookstore.dao.jdbc.OrderDAOImpl;
import bookstore.dao.jdbc.OrderDetailDAOImpl;
import bookstore.logic.AbstractOrderLogic;

@UsedPojo
public class OrderLogicWrapper extends AbstractOrderLogic {

	private final BookDAO bookdao = new BookDAOImpl();
	private final CustomerDAO customerdao = new CustomerDAOImpl();
	private final OrderDAO orderdao = new OrderDAOImpl();
	private final OrderDetailDAO odetaildao = new OrderDetailDAOImpl();
	private static final Logger log = Logger.getLogger(OrderLogicWrapper.class.getName());

	@Override
	protected BookDAO getBookDAO() {
		return bookdao;
	}
	@Override
	protected CustomerDAO getCustomerDAO() {
		return customerdao;
	}
	@Override
	protected OrderDAO getOrderDAO() {
		return orderdao;
	}
	@Override
	protected OrderDetailDAO getOrderDetailDAO() {
		return odetaildao;
	}
	@Override
	protected Logger getLogger() {
		return log;
	}

}
