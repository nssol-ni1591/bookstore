package bookstore.logic;

import bookstore.dao.impl.CustomerDAOImpl;

public class CustomerLogicImpl2 extends CustomerLogicImpl {

	public CustomerLogicImpl2() {
		super.setCustomerdao(new CustomerDAOImpl());
	}

}
