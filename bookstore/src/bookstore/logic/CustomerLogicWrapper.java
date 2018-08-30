package bookstore.logic;

import bookstore.dao.impl.CustomerDAOImpl;

public class CustomerLogicWrapper extends CustomerLogicImpl {

	public CustomerLogicWrapper() {
		super.setCustomerdao(new CustomerDAOImpl());
	}

}
