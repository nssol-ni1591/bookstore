package bookstore.service.classic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import bookstore.annotation.UsedClassic;
import bookstore.dao.CustomerDAO;
import bookstore.dao.jdbc.CustomerDAOImpl;
import bookstore.dao.jdbc.DB;
import bookstore.service.AbstractCustomerService;

@UsedClassic
public class CustomerServiceWrapper extends AbstractCustomerService<Connection> {

	private final CustomerDAO<Connection> customerdao = new CustomerDAOImpl<>();
	private static final Logger log = Logger.getLogger(CustomerServiceWrapper.class.getName());

	private Connection con = null;

	@Override
	protected CustomerDAO<Connection> getCustomerDAO() {
		return customerdao;
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
	public boolean createCustomer(String uid
			, String password
			, String name
			, String email
			) throws SQLException
	{
		boolean rc = false;
		con = DB.createConnection();
		try {
			rc = super.createCustomer(uid, password, name, email);
			con.commit();
			return rc;
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
