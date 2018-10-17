package bookstore.jsf.bean.ejblocal;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import bookstore.jsf.bean.AbstractOrderBean;
import bookstore.service.BookService;
import bookstore.service.CustomerService;
import bookstore.service.OrderService;

@Named
@RequestScoped
public class OrderBean2 extends AbstractOrderBean {

	//@LocalBean
	@EJB private BookService bookService;
	@EJB private CustomerService customerService;
	@EJB private OrderService orderService;


	@Override
	protected BookService getBookService() {
		return bookService;
	}
	@Override
	protected CustomerService getCustomerService() {
		return customerService;
	}
	@Override
	protected OrderService getOrderService() {
		return orderService;
	}

	@Override
	protected String getBookStorePage() {
		return "BookStore2";
	}
	@Override
	protected String getCheckPage() {
		return "Check2";
	}
	@Override
	protected String getOrderPage() {
		return "Order2";
	}
	@Override
	protected String getOrderListPage() {
		return "OrderList";
	}

}