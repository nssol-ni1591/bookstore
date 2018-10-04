package bookstore.jsf.bean.ejbbmt;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import bookstore.jsf.bean.AbstractOrderBean;
import bookstore.service.BookService;
import bookstore.service.CustomerService;
import bookstore.service.OrderService;

@Named
@RequestScoped
public class OrderBean4 extends AbstractOrderBean {

	@EJB(mappedName="BookServiceBmtWrapper") private BookService bookService;
	@EJB(mappedName="CustomerServiceBmtWrapper") private CustomerService customerService;
	@EJB(mappedName="OrderServiceBmtWrapper") private OrderService orderService;

	protected BookService getBookService() {
		return bookService;
	}
	protected CustomerService getCustomerService() {
		return customerService;
	}
	protected OrderService getOrderService() {
		return orderService;
	}

	protected String getBookStorePage() {
		return "BookStore4";
	}
	protected String getCheckPage() {
		return "Check4";
	}
	protected String getOrderPage() {
		return "Order4";
	}
	protected String getOrderListPage() {
		return "OrderList";
	}

}