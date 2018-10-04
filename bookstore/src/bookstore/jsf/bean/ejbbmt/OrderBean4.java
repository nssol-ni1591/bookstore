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

	@EJB(mappedName="BookLogicBmtWrapper") private BookService bookLogic;
	@EJB(mappedName="CustomerLogicBmtWrapper") private CustomerService customerLogic;
	@EJB(mappedName="OrderLogicBmtWrapper") private OrderService orderLogic;

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