package bookstore.logic.pojo;

import bookstore.annotation.UsedPojo;
import bookstore.dao.CustomerDAO;
import bookstore.dao.impl.CustomerDAOImpl;
import bookstore.logic.impl.CustomerLogicImpl;

@UsedPojo
public class CustomerLogicWrapper extends CustomerLogicImpl {

	public CustomerLogicWrapper() {
		CustomerDAO customerdao = new CustomerDAOImpl();
		System.out.println("weld.CustomerLogicWrapper.init: customerdao=" + customerdao);
		super.setCustomerdao(customerdao);
	}

}
