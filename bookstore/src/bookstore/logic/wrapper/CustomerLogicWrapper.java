package bookstore.logic.wrapper;

import bookstore.dao.impl.CustomerDAOImpl;
import bookstore.logic.CustomerLogicImpl;

public class CustomerLogicWrapper extends CustomerLogicImpl {

	public CustomerLogicWrapper() {
		super.setCustomerdao(new CustomerDAOImpl());
	}

}
