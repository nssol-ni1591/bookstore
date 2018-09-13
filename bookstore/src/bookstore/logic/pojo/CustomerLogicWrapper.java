package bookstore.logic.pojo;

import java.util.logging.Level;
import java.util.logging.Logger;

import bookstore.annotation.UsedPojo;
import bookstore.dao.CustomerDAO;
import bookstore.dao.jdbc.CustomerDAOImpl;
import bookstore.logic.impl.CustomerLogicImpl;

@UsedPojo
public class CustomerLogicWrapper extends CustomerLogicImpl {

	private static Logger log;

	public CustomerLogicWrapper() {
		CustomerDAO customerdao = new CustomerDAOImpl();
		getLogger().log(Level.INFO, "customerdao={0}", customerdao);
		super.setCustomerdao(customerdao);
	}

	@Override
	protected Logger getLogger() {
		if (log == null) {
			return Logger.getLogger(CustomerLogicWrapper.class.getName());
		}
		return log;
	}

}
