package bookstore.jsf.bean.ejbbmt;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import bookstore.jsf.bean.AbstractOrderBean;
import bookstore.service.BookService;
import bookstore.service.CustomerService;
import bookstore.service.OrderService;
import bookstore.service.ejb.BookServiceRemote;
import bookstore.service.ejb.CustomerServiceRemote;
import bookstore.service.ejb.OrderServiceRemote;

@Named
@RequestScoped
public class OrderBean4 extends AbstractOrderBean {

	// @Remote
	@EJB(mappedName="BookServiceBmtWrapper") private BookServiceRemote bookService;
	@EJB(mappedName="CustomerServiceBmtWrapper") private CustomerServiceRemote customerService;
	@EJB(mappedName="OrderServiceBmtWrapper") private OrderServiceRemote orderService;


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
		return "BookStore4";
	}
	@Override
	protected String getCheckPage() {
		return "Check4";
	}
	@Override
	protected String getOrderPage() {
		return "Order4";
	}
	@Override
	protected String getOrderListPage() {
		return "OrderList";
	}

}
