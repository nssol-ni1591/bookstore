package bookstore.servlet3;

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

import bookstore.service.BookService;
import bookstore.util.Messages;

/*
 * ServletはScopeアノテーションを付加するとCDI管理下になる
 * @Injectの@Qualifierでservice層を切替える
 */
public abstract class AbstractCheckoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected abstract BookService getBookService();
	protected abstract Logger getLogger();
	protected abstract String getNextPage();
	protected abstract String getErrorPage();


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		BookService bookService = getBookService();
		Logger log = getLogger();
		String nextPage = getNextPage();
		String errorPage = getErrorPage();

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
				dispatcher = req.getRequestDispatcher(errorPage);
			}
			else {
				try {
					httpSession.setAttribute("ItemsToBuy", bookService.createVCheckout(selectedItems));
					dispatcher = req.getRequestDispatcher(nextPage);
				}
				catch (SQLException e) {
					log.log(Level.SEVERE, "", e);
					errors.add("productalart", "error.system.exception");
					dispatcher = req.getRequestDispatcher(errorPage);
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

