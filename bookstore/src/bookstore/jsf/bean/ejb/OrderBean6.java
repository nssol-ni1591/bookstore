package bookstore.jsf.bean.ejb;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import bookstore.jsf.bean.AbstractOrderBean;
import bookstore.service.BookService;
import bookstore.service.CustomerService;
import bookstore.service.OrderService;

@Named
@RequestScoped
public class OrderBean6 extends AbstractOrderBean {

	@EJB(mappedName="BookServiceEjbBmtWrapper") private BookService bookService;
	@EJB(mappedName="CustomerServiceEjbBmtWrapper") private CustomerService customerService;
	@EJB(mappedName="OrderServiceEjbBmtWrapper") private OrderService orderService;

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
		return "BookStore6";
	}
	protected String getCheckPage() {
		return "Check6";
	}
	protected String getOrderPage() {
		return "Order6";
	}
	protected String getOrderListPage() {
		return "OrderList";
	}

}
