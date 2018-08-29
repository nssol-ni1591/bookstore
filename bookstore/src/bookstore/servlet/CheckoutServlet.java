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

import bookstore.logic.BookLogic;
import bookstore.logic.BookLogicImpl2;

public class CheckoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		HttpSession httpSession = req.getSession(false);

		Map<String, String> errors = new HashMap<>();
		RequestDispatcher dispatcher;

		if (httpSession == null) {
			dispatcher = req.getRequestDispatcher("sessionError.vm");
		}
		else {
			List<String> selectedItems = (List<String>) httpSession.getAttribute("Cart");
			if (selectedItems == null || selectedItems.size() == 0) {
				errors.put("illegallogin", "error.login.pwmismatch");
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
	/*
	public void setBookLogic(BookLogic bookLogic) {
		this.bookLogic = bookLogic;
	}
	*/
}
