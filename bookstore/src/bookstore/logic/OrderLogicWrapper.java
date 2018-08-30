package bookstore.logic;

import bookstore.dao.impl.BookDAOImpl;
import bookstore.dao.impl.CustomerDAOImpl;
import bookstore.dao.impl.OrderDAOImpl;
import bookstore.dao.impl.OrderDetailDAOImpl;

public class OrderLogicWrapper extends OrderLogicImpl {

	public OrderLogicWrapper() {
		super.setBookdao(new BookDAOImpl());
		super.setCustomerdao(new CustomerDAOImpl());
		super.setOrderdao(new OrderDAOImpl());
		super.setOrderdetaildao(new OrderDetailDAOImpl());
	}

}
