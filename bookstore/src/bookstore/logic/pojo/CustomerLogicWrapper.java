package bookstore.logic.pojo;

import java.sql.Connection;
import java.util.logging.Logger;

import bookstore.annotation.UsedPojo;
import bookstore.dao.CustomerDAO;
import bookstore.dao.jdbc.CustomerDAOImpl;
import bookstore.dao.jdbc.DB;
import bookstore.logic.AbstractCustomerLogic;

@UsedPojo
public class CustomerLogicWrapper extends AbstractCustomerLogic<Connection> {

	private final CustomerDAO<Connection> customerdao = new CustomerDAOImpl<>();
	private static final Logger log = Logger.getLogger(CustomerLogicWrapper.class.getName());

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
	public boolean createCustomer(String inUid
			, String inPassword
			, String inName
			, String inEmail) throws Exception {
		boolean rc = false;
		con = DB.createConnection();
		try {
			rc = super.createCustomer(inUid, inPassword, inName, inEmail);
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
