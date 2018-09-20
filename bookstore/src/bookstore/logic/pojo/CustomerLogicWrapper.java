package bookstore.logic.pojo;

import java.util.logging.Logger;

import bookstore.annotation.UsedPojo;
import bookstore.dao.CustomerDAO;
import bookstore.dao.jdbc.CustomerDAOImpl;
import bookstore.logic.AbstractCustomerLogic;

@UsedPojo
public class CustomerLogicWrapper extends AbstractCustomerLogic {

	private final CustomerDAO customerdao = new CustomerDAOImpl();
	private static final Logger log = Logger.getLogger(CustomerLogicWrapper.class.getName());

	@Override
	protected CustomerDAO getCustomerDAO() {
		return customerdao;
	}
	@Override
	protected Logger getLogger() {
		return log;
	}

}
