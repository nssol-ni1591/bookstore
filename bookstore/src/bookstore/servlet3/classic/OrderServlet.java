package bookstore.servlet3.classic;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bookstore.annotation.UsedClassic;
import bookstore.service.CustomerService;
import bookstore.service.OrderService;
import bookstore.util.Messages;

/*
 * ServletはScopeアノテーションを付加するとCDI管理下になる
 * @Injectの@Qualifierでservice層を切替える
 */
@WebServlet(urlPatterns="/OrderServlet4")
@RequestScoped
public class OrderServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Inject @UsedClassic private CustomerService customerService;
	@Inject @UsedClassic private OrderService orderService;
	@Inject private Logger log;


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
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
				dispatcher = req.getRequestDispatcher("Check4.jsp");
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