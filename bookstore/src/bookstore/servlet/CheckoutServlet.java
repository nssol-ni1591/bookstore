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

import bookstore.logic.BookLogic;
import bookstore.logic.weld.BookLogicWrapper;
import bookstore.util.Messages;

/*
 * Logic LayerÇÃéQè∆Ç≈DAOÇêÿë÷Ç¶ÇÈ
 * ÅEjdbc native - bookstore.logic.wrapper.xxxxLogicWrapper
 * ÅEeclipselink - bookstore.logic.jpa.xxxxLogicWrapper
 */
public class CheckoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) {

		HttpSession httpSession = req.getSession(false);

		Messages errors = new Messages(req);
		RequestDispatcher dispatcher;

		if (httpSession == null) {
			dispatcher = req.getRequestDispatcher("sessionError.html");
		}
		else {
			@SuppressWarnings("unchecked")
			List<String> selectedItems = (List<String>) httpSession.getAttribute("Cart");
			if (selectedItems == null || selectedItems.isEmpty()) {
				errors.add("productalart", "error.checkout.noselected");

				dispatcher = req.getRequestDispatcher("BookStore.jsp");
			}
			else {
				BookLogic bookLogic = new BookLogicWrapper();
				httpSession.setAttribute("ItemsToBuy", bookLogic.createVCheckout(selectedItems));
				dispatcher = req.getRequestDispatcher("Check.jsp");
			}
		}
		try {
			dispatcher.forward(req, res);
		}
		catch (ServletException | IOException e) {
			Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, "", e);
		}
	}
}
