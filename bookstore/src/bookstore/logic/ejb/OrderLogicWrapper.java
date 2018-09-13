package bookstore.logic.ejb;

import bookstore.annotation.UsedEjb;
import bookstore.annotation.UsedOpenJpa;
import bookstore.dao.BookDAO;
import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.dao.OrderDetailDAO;
import bookstore.logic.impl.OrderLogicImpl;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

@UsedEjb
@Stateless
public class OrderLogicWrapper extends OrderLogicImpl {

	@Inject @UsedOpenJpa private BookDAO bookdao;
	@Inject @UsedOpenJpa private CustomerDAO customerdao;
	@Inject @UsedOpenJpa private OrderDAO orderdao;
	@Inject @UsedOpenJpa private OrderDetailDAO orderdetaildao;

	@PostConstruct
	public void init() {
		super.setBookdao(bookdao);
		super.setCustomerdao(customerdao);
		super.setOrderdao(orderdao);
		super.setOrderdetaildao(orderdetaildao);
	}

}
