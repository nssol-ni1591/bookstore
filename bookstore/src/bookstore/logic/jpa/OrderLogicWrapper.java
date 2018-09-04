package bookstore.logic.jpa;

import bookstore.dao.eclipselink.BookDAOImpl;
import bookstore.dao.eclipselink.CustomerDAOImpl;
import bookstore.dao.eclipselink.OrderDAOImpl;
import bookstore.dao.eclipselink.OrderDetailDAOImpl;
import bookstore.logic.OrderLogicImpl;

public class OrderLogicWrapper extends OrderLogicImpl {

	public OrderLogicWrapper() {
		super.setBookdao(new BookDAOImpl());
		super.setCustomerdao(new CustomerDAOImpl());
		super.setOrderdao(new OrderDAOImpl());
		super.setOrderdetaildao(new OrderDetailDAOImpl());
	}

}
