package bookstore.jsf.bean;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import bookstore.logic.BookLogic;
import bookstore.logic.CustomerLogic;
import bookstore.logic.OrderLogic;

@Named
@RequestScoped
public class OrderBean4 extends AbstractOrderBean {

	@EJB(mappedName="BookLogicBmtWrapper") private BookLogic bookLogic;
	@EJB(mappedName="CustomerLogicBmtWrapper") private CustomerLogic customerLogic;
	@EJB(mappedName="OrderLogicBmtWrapper") private OrderLogic orderLogic;

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