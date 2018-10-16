package bookstore.servlet3;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bookstore.service.CustomerService;
import bookstore.service.OrderService;
import bookstore.util.Messages;

/*
 * ServletはScopeアノテーションを付加するとCDI管理下になる
 * @Injectの@Qualifierでservice層を切替える
 */
public abstract class AbstractOrderServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected abstract CustomerService getCustomerService();
	protected abstract OrderService getOrderService();
	protected abstract Logger getLogger();
	protected abstract String getErrorPage();


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		CustomerService customerService = getCustomerService();
		OrderService orderService = getOrderService();
		Logger log = getLogger();
		String errorPage = getErrorPage();

		HttpSession httpSession = req.getSession(false);
		RequestDispatcher dispatcher;

		if (httpSession == null) {
			dispatcher = req.getRequestDispatcher("sessionError.html");
		}
		else {
			String uid = (String) httpSession.getAttribute("Login");
			@SuppressWarnings("unchecked")
			List<String> cart = (List<String>)httpSession.getAttribute("Cart");
			try {
				orderService.orderBooks(uid, cart);
				req.setAttribute("Customer", customerService.createVCustomer(uid));
				dispatcher = req.getRequestDispatcher("Order.jsp");
			}
			catch (Exception e) {
				log.log(Level.SEVERE, "", e);
				Messages errors = new Messages(req);
				errors.add("orderalert", "error.system.exception");
				dispatcher = req.getRequestDispatcher(errorPage);
			}
		}
		try {
			dispatcher.forward(req, res);
		}
		catch (ServletException | IOException e) {
			log.log(Level.SEVERE, "", e);
		}
	}

}
