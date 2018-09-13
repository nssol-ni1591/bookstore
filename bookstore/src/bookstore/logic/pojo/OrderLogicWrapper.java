package bookstore.logic.pojo;

import bookstore.annotation.UsedPojo;
import bookstore.dao.jdbc.BookDAOImpl;
import bookstore.dao.jdbc.CustomerDAOImpl;
import bookstore.dao.jdbc.OrderDAOImpl;
import bookstore.dao.jdbc.OrderDetailDAOImpl;
import bookstore.logic.impl.OrderLogicImpl;

@UsedPojo
public class OrderLogicWrapper extends OrderLogicImpl {

	public OrderLogicWrapper() {
		super.setBookdao(new BookDAOImpl());
		super.setCustomerdao(new CustomerDAOImpl());
		super.setOrderdao(new OrderDAOImpl());
		super.setOrderdetaildao(new OrderDetailDAOImpl());
	}

}
