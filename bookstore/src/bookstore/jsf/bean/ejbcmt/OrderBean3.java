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

	@EJB(mappedName="BookLogicCmtWrapper") private BookService bookLogic;
	@EJB(mappedName="CustomerLogicCmtWrapper") private CustomerService customerLogic;
	@EJB(mappedName="OrderLogicCmtWrapper") private OrderService orderLogic;

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