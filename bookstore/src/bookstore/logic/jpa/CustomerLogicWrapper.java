package bookstore.logic.jpa;

import bookstore.dao.eclipselink.CustomerDAOImpl;
import bookstore.logic.CustomerLogicImpl;

public class CustomerLogicWrapper extends CustomerLogicImpl {

	public CustomerLogicWrapper() {
		super.setCustomerdao(new CustomerDAOImpl());
	}

}
