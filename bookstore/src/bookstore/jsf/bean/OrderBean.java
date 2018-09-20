package bookstore.jsf.bean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import bookstore.annotation.UsedWeld;
import bookstore.logic.BookLogic;
import bookstore.logic.CustomerLogic;
import bookstore.logic.OrderLogic;

@Named
@RequestScoped
public class OrderBean extends AbstractOrderBean {

	@Inject @UsedWeld private BookLogic bookLogic;
	@Inject @UsedWeld private CustomerLogic customerLogic;
	@Inject @UsedWeld private OrderLogic orderLogic;

	protected BookLogic getBookLogic() {
		return bookLogic;
	}
	protected CustomerLogic getCustomerLogic() {
		return customerLogic;
	}
	protected OrderLogic getOrderLogic() {
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