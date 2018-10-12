package bookstore.jsf.bean.weld;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import bookstore.annotation.UsedWeld;
import bookstore.jsf.bean.AbstractOrderBean;
import bookstore.service.BookService;
import bookstore.service.CustomerService;
import bookstore.service.OrderService;

@Named
@RequestScoped
public class OrderBean extends AbstractOrderBean {

	@Inject @UsedWeld private BookService bookService;
	@Inject @UsedWeld private CustomerService customerService;
	@Inject @UsedWeld private OrderService orderService;

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
		return "BookStore";
	}
	protected String getCheckPage() {
		return "Check";
	}
	protected String getOrderPage() {
		return "Order";
	}
	protected String getOrderListPage() {
		return "OrderList";
	}

}