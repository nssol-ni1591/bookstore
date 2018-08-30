package bookstore.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bookstore.logic.BookLogic;
import bookstore.logic.BookLogicImpl2;
import bookstore.util.Messages;

public class CheckoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		HttpSession httpSession = req.getSession(false);

		Messages errors = new Messages();
		RequestDispatcher dispatcher;

		if (httpSession == null) {
			dispatcher = req.getRequestDispatcher("sessionError.html");
		}
		else {
			@SuppressWarnings("unchecked")
			List<String> selectedItems = (List<String>) httpSession.getAttribute("Cart");
			if (selectedItems == null || selectedItems.isEmpty()) {
				errors.add("productalart", "error.checkout.noselected");
				req.setAttribute("errors", errors);

				dispatcher = req.getRequestDispatcher("BookStore.jsp");
			}
			else {
				BookLogic bookLogic = new BookLogicImpl2();
				httpSession.setAttribute("ItemsToBuy", bookLogic.createVCheckout(selectedItems));
				dispatcher = req.getRequestDispatcher("Check.jsp");
			}
		}
		dispatcher.forward(req, res);
	}
}
