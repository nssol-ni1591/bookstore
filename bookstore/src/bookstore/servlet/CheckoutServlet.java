package bookstore.servlet;

import java.io.IOException;
import java.sql.SQLException;
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
import bookstore.logic.pojo.BookLogicWrapper;
import bookstore.util.Messages;

/*
 * Logic Layerの参照でDAOを切替える
 * ・jdbc native - bookstore.logic.wrapper.xxxxLogicWrapper
 * ・eclipselink - bookstore.logic.jpa.xxxxLogicWrapper
 */
public class CheckoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(CheckoutServlet.class.getName());

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
				try {
					BookLogic bookLogic = new BookLogicWrapper();
					httpSession.setAttribute("ItemsToBuy", bookLogic.createVCheckout(selectedItems));
					dispatcher = req.getRequestDispatcher("Check.jsp");
				}
				catch (SQLException e) {
					log.log(Level.SEVERE, "", e);
					errors.add("productalart", "error.system.exception");
					dispatcher = req.getRequestDispatcher("BookStore.jsp");
				}
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
