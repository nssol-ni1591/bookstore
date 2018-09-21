package bookstore.logic.pojo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import bookstore.annotation.UsedPojo;
import bookstore.dao.BookDAO;
import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.dao.OrderDetailDAO;
import bookstore.dao.jdbc.BookDAOImpl;
import bookstore.dao.jdbc.CustomerDAOImpl;
import bookstore.dao.jdbc.DB;
import bookstore.dao.jdbc.OrderDAOImpl;
import bookstore.dao.jdbc.OrderDetailDAOImpl;
import bookstore.logic.AbstractOrderLogic;

@UsedPojo
public class OrderLogicWrapper extends AbstractOrderLogic<Connection> {

	private final BookDAO<Connection> bookdao = new BookDAOImpl<>();
	private final CustomerDAO<Connection> customerdao = new CustomerDAOImpl<>();
	private final OrderDAO<Connection> orderdao = new OrderDAOImpl<>();
	private final OrderDetailDAO<Connection> odetaildao = new OrderDetailDAOImpl<>();
	private static final Logger log = Logger.getLogger(OrderLogicWrapper.class.getName());

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
		try {
			return DB.createConnection();
		}
		catch (SQLException e) {
			log.log(Level.SEVERE, "In createConnection", e);
		}
		return null;
	}

	@Override
	public void orderBooks(String inUid, List<String> inISBNs) throws Exception {
		Connection con = getManager();
		try {
			super.orderBooks(inUid, inISBNs);
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
		}
	}
}
