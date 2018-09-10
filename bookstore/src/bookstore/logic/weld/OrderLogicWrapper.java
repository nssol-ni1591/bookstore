package bookstore.logic.weld;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import bookstore.annotation.UsedWeld;
import bookstore.annotation.UsedEclipselink;
import bookstore.dao.BookDAO;
import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.dao.OrderDetailDAO;
import bookstore.logic.impl.OrderLogicImpl;

@UsedWeld
@Dependent
public class OrderLogicWrapper extends OrderLogicImpl {

	@Inject @UsedEclipselink private BookDAO bookdao;
	@Inject @UsedEclipselink private CustomerDAO customerdao;
	@Inject @UsedEclipselink private OrderDAO orderdao;
	@Inject @UsedEclipselink private OrderDetailDAO orderdetaildao;

	@PostConstruct
	public void init() {
		super.setBookdao(bookdao);
		super.setCustomerdao(customerdao);
		super.setOrderdao(orderdao);
		super.setOrderdetaildao(orderdetaildao);
	}

}
