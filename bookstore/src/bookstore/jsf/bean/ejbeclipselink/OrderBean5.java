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

	@EJB(mappedName="BookLogicEclipseLinkWrapper") private BookService bookLogic;
	@EJB(mappedName="CustomerLogicEclipseLinkWrapper") private CustomerService customerLogic;
	@EJB(mappedName="OrderLogicEclipseLinkWrapper") private OrderService orderLogic;

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
