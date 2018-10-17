package bookstore.servlet3.weld;

import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;

import bookstore.annotation.UsedWeld;
import bookstore.service.CustomerService;
import bookstore.service.OrderService;
import bookstore.servlet3.AbstractOrderServlet;

/*
 * Servlet��Scope�A�m�e�[�V������t�������CDI�Ǘ����ɂȂ�
 * @Inject��@Qualifier��service�w��ؑւ���
 */
@WebServlet(urlPatterns="/OrderServlet3")
@RequestScoped
public class OrderServlet extends AbstractOrderServlet {

	private static final long serialVersionUID = 1L;

	@Inject @UsedWeld private CustomerService customerService;
	@Inject @UsedWeld private OrderService orderService;
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
		return "Check3.jsp";
	}

}
