package bookstore.logic.pojo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
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
		try {
			return DB.createConnection();
		}
		catch (SQLException e) {
			log.log(Level.SEVERE, "", e);
		}
		return null;
	}

}
