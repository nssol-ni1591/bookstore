package bookstore.jsf.bean.ejb;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import bookstore.jsf.bean.AbstractOrderBean;
import bookstore.service.BookService;
import bookstore.service.CustomerService;
import bookstore.service.OrderService;
import bookstore.service.ejb.cmt.BookServiceWrapper;
import bookstore.service.ejb.cmt.CustomerServiceWrapper;
import bookstore.service.ejb.cmt.OrderServiceWrapper;

@Named
@RequestScoped
public class OrderBean2 extends AbstractOrderBean {

	@EJB private BookServiceWrapper bookLogic;
	@EJB private CustomerServiceWrapper customerLogic;
	@EJB private OrderServiceWrapper orderLogic;

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