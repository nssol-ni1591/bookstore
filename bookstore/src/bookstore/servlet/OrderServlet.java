package bookstore.servlet;

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
import bookstore.service.pojo.CustomerServiceWrapper;
import bookstore.service.pojo.OrderServiceWrapper;
import bookstore.util.Messages;

/*
 * Service Layerの参照でDAOを切替える
 * ・jdbc native - bookstore.service.wrapper.xxxxServiceWrapper
 * ・eclipselink - bookstore.service.jpa.xxxxServiceWrapper
 */
public class OrderServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(OrderServlet.class.getName());

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
			OrderService orderService = new OrderServiceWrapper();
			CustomerService customerService = new CustomerServiceWrapper();

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
				dispatcher = req.getRequestDispatcher("Check.jsp");
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