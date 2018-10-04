package bookstore.jsf.bean.eclipselink;

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

	@Inject @UsedWeld private BookService bookLogic;
	@Inject @UsedWeld private CustomerService customerLogic;
	@Inject @UsedWeld private OrderService orderLogic;

	protected BookService getBookLogic() {
		return bookLogic;
	}
	protected CustomerService getCustomerLogic() {
		return customerLogic;
	}
	protected OrderService getOrderLogic() {
		return orderLogic;
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