package bookstore.jsf.bean;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import bookstore.logic.BookLogic;
import bookstore.logic.CustomerLogic;
import bookstore.logic.OrderLogic;

@Named
@RequestScoped
public class OrderBean3 extends AbstractOrderBean {

	@EJB(mappedName="BookLogicCmtWrapper") private BookLogic bookLogic;
	@EJB(mappedName="CustomerLogicCmtWrapper") private CustomerLogic customerLogic;
	@EJB(mappedName="OrderLogicCmtWrapper") private OrderLogic orderLogic;

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