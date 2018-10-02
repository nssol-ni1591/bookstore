package bookstore.jsf.bean.ejb;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import bookstore.jsf.bean.AbstractOrderBean;
import bookstore.logic.BookLogic;
import bookstore.logic.CustomerLogic;
import bookstore.logic.OrderLogic;
import bookstore.logic.ejb.cmt.BookLogicWrapper;
import bookstore.logic.ejb.cmt.CustomerLogicWrapper;
import bookstore.logic.ejb.cmt.OrderLogicWrapper;

@Named
@RequestScoped
public class OrderBean2 extends AbstractOrderBean {

	@EJB private BookLogicWrapper bookLogic;
	@EJB private CustomerLogicWrapper customerLogic;
	@EJB private OrderLogicWrapper orderLogic;

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
		return "BookStore2";
	}
	protected String getCheckPage() {
		return "Check2";
	}
	protected String getOrderPage() {
		return "Order2";
	}
	protected String getOrderListPage() {
		return "OrderList";
	}

}