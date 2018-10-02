package bookstore.jsf.bean.ejbeclipselink;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import bookstore.jsf.bean.AbstractOrderBean;
import bookstore.logic.BookLogic;
import bookstore.logic.CustomerLogic;
import bookstore.logic.OrderLogic;

@Named
@RequestScoped
public class OrderBean5 extends AbstractOrderBean {

	@EJB(mappedName="BookLogicEclipseLinkWrapper") private BookLogic bookLogic;
	@EJB(mappedName="CustomerLogicEclipseLinkWrapper") private CustomerLogic customerLogic;
	@EJB(mappedName="OrderLogicEclipseLinkWrapper") private OrderLogic orderLogic;

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
