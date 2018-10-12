package bookstore.service.classic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import bookstore.annotation.UsedClassic;
import bookstore.dao.BookDAO;
import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.dao.OrderDetailDAO;
import bookstore.dao.jdbc.BookDAOImpl;
import bookstore.dao.jdbc.CustomerDAOImpl;
import bookstore.dao.jdbc.DB;
import bookstore.dao.jdbc.OrderDAOImpl;
import bookstore.dao.jdbc.OrderDetailDAOImpl;
import bookstore.service.AbstractOrderService;

@UsedClassic
public class OrderServiceWrapper extends AbstractOrderService<Connection> {

	private final BookDAO<Connection> bookdao = new BookDAOImpl<>();
	private final CustomerDAO<Connection> customerdao = new CustomerDAOImpl<>();
	private final OrderDAO<Connection> orderdao = new OrderDAOImpl<>();
	private final OrderDetailDAO<Connection> odetaildao = new OrderDetailDAOImpl<>();

	private static final Logger log = Logger.getLogger(OrderServiceWrapper.class.getName());

	private Connection con = null;

	@Override
	protected BookDAO<Connection> getBookDAO() {
		return bookdao;
	}
	@Override
	protected CustomerDAO<Connection> getCustomerDAO() {
		return customerdao;
	}
	@Override
	protected OrderDAO<Connection> getOrderDAO() {
		return orderdao;
	}
	@Override
	protected OrderDetailDAO<Connection> getOrderDetailDAO() {
		return odetaildao;
	}
	@Override
	protected Logger getLogger() {
		return log;
	}
	@Override
	protected Connection getManager() {
		return con;
	}

	@Override
	public void orderBooks(String uid, List<String> isbns) throws SQLException {
		con = DB.createConnection();
		try {
			super.orderBooks(uid, isbns);
			con.commit();
		}
		catch (Exception e) {
			if (con != null) {
				con.rollback();
			}
			throw e;
		}
		finally {
			if (con != null) {
				con.close();
			}
			con = null;
		}
	}
}
