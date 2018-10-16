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
 * Servlet��Scope�A�m�e�[�V������t�������CDI�Ǘ����ɂȂ�
 * @Inject��@Qualifier��service�w��ؑւ���
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
