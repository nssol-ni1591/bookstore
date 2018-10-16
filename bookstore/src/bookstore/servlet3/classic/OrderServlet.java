package bookstore.servlet3.classic;

import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;

import bookstore.annotation.UsedClassic;
import bookstore.service.CustomerService;
import bookstore.service.OrderService;
import bookstore.servlet3.AbstractOrderServlet;

/*
 * ServletはScopeアノテーションを付加するとCDI管理下になる
 * @Injectの@Qualifierでservice層を切替える
 */
@WebServlet(urlPatterns="/OrderServlet4")
@RequestScoped
public class OrderServlet extends AbstractOrderServlet {

	private static final long serialVersionUID = 1L;

	@Inject @UsedClassic private CustomerService customerService;
	@Inject @UsedClassic private OrderService orderService;
	@Inject private Logger log;


	@Override
	protected CustomerService getCustomerService() {
		return customerService;
	}
	@Override
	protected OrderService getOrderService() {
		return orderService;
	}
	@Override
	protected Logger getLogger() {
		return log;
	}
	@Override
	protected String getErrorPage() {
		return "Check4.jsp";
	}

}
