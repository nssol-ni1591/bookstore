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

import bookstore.logic.CustomerLogic;
import bookstore.logic.CustomerLogicImpl2;
import bookstore.logic.OrderLogic;
import bookstore.logic.OrderLogicImpl2;

public class OrderServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

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
			OrderLogic orderLogic = new OrderLogicImpl2();
			CustomerLogic customerLogic = new CustomerLogicImpl2();

			String uid = (String) httpSession.getAttribute("Login");

			@SuppressWarnings("unchecked")
			List<String> cart = (List<String>)httpSession.getAttribute("Cart");

			orderLogic.orderBooks(uid, cart);

			req.setAttribute("Customer", customerLogic.createVCustomer(uid));

			dispatcher = req.getRequestDispatcher("Order.jsp");
		}
		try {
			dispatcher.forward(req, res);
		}
		catch (ServletException | IOException e) {
			Logger.getLogger(OrderServlet.class.getName()).log(Level.SEVERE, "", e);
		}
	}
}