package bookstore.jsf.bean.ejbeclipselink;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import bookstore.jsf.bean.AbstractOrderBean;
import bookstore.service.BookService;
import bookstore.service.CustomerService;
import bookstore.service.OrderService;

@Named
@RequestScoped
public class OrderBean5 extends AbstractOrderBean {

	@EJB(mappedName="BookServiceEclipseLinkWrapper") private BookService bookService;
	@EJB(mappedName="CustomerServiceEclipseLinkWrapper") private CustomerService customerService;
	@EJB(mappedName="OrderServiceEclipseLinkWrapper") private OrderService orderService;

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
		return "BookStore5";
	}
	protected String getCheckPage() {
		return "Check5";
	}
	protected String getOrderPage() {
		return "Order5";
	}
	protected String getOrderListPage() {
		return "OrderList";
	}

}
