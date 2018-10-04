package bookstore.jsf.bean.ejbcmt;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import bookstore.jsf.bean.AbstractOrderBean;
import bookstore.service.BookService;
import bookstore.service.CustomerService;
import bookstore.service.OrderService;

@Named
@RequestScoped
public class OrderBean3 extends AbstractOrderBean {

	@EJB(mappedName="BookServiceCmtWrapper") private BookService bookService;
	@EJB(mappedName="CustomerServiceCmtWrapper") private CustomerService customerService;
	@EJB(mappedName="OrderServiceCmtWrapper") private OrderService orderService;

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
		return "BookStore3";
	}
	protected String getCheckPage() {
		return "Check3";
	}
	protected String getOrderPage() {
		return "Order3";
	}
	protected String getOrderListPage() {
		return "OrderList";
	}

}