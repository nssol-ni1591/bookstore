package bookstore.jsf.bean.ejbcmt;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import bookstore.jsf.bean.AbstractOrderBean;
import bookstore.service.BookService;
import bookstore.service.CustomerService;
import bookstore.service.OrderService;
import bookstore.service.ejb.BookServiceLocal;
import bookstore.service.ejb.CustomerServiceLocal;
import bookstore.service.ejb.OrderServiceLocal;

@Named
@RequestScoped
public class OrderBean3 extends AbstractOrderBean {

	//@Local
	@EJB(mappedName="BookServiceCmtWrapper") private BookServiceLocal bookService;
	@EJB(mappedName="CustomerServiceCmtWrapper") private CustomerServiceLocal customerService;
	@EJB(mappedName="OrderServiceCmtWrapper") private OrderServiceLocal orderService;


	@Override
	protected BookService getBookService() {
		return bookService;
	}
	@Override
	protected CustomerService getCustomerService() {
		return customerService;
	}
	@Override
	protected OrderService getOrderService() {
		return orderService;
	}

	@Override
	protected String getBookStorePage() {
		return "BookStore3";
	}
	@Override
	protected String getCheckPage() {
		return "Check3";
	}
	@Override
	protected String getOrderPage() {
		return "Order3";
	}
	@Override
	protected String getOrderListPage() {
		return "OrderList";
	}

}