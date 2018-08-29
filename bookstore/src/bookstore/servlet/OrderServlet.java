package bookstore.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession httpSession = req.getSession(false);

		RequestDispatcher dispatcher;

		if (httpSession == null) {
			dispatcher = req.getRequestDispatcher("sessionError.vm");
		}
		else {
			List<String> selectedItems = (List<String>)httpSession.getAttribute("Cart");
			OrderLogic orderLogic = new OrderLogicImpl2();
			CustomerLogic customerLogic = new CustomerLogicImpl2();

			String uid = (String) httpSession.getAttribute("Login");

			List<String> cart = (List<String>)httpSession.getAttribute("Cart");

			orderLogic.orderBooks(uid, cart);

			req.setAttribute("Customer", customerLogic.createVCustomer(uid));

			dispatcher = req.getRequestDispatcher("Order.jsp");
		}
		dispatcher.forward(req, res);
	}
	/*
	public void setOrderLogic(OrderLogic orderLogic) {
		this.orderLogic = orderLogic;
	}

	public void setCustomerLogic(CustomerLogic customerLogic) {
		this.customerLogic = customerLogic;
	}
	*/
}