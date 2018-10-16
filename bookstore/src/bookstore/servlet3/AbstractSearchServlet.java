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
import bookstore.vbean.VBook;

/*
 * ServletはScopeアノテーションを付加するとCDI管理下になる
 * @Injectの@Qualifierでservice層を切替える
 */
public abstract class AbstractSearchServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected abstract BookService getBookService();
	protected abstract Logger getLogger();
	protected abstract String getNextPage();


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		BookService bookService = getBookService();
		Logger log = getLogger();
		String nextPage = getNextPage();

		String keyword = req.getParameter("keyword");
		Messages errors = new Messages(req);
		RequestDispatcher dispatcher;

		HttpSession httpSession = req.getSession(false);
		if (httpSession == null) {
			dispatcher = req.getRequestDispatcher("sessionError.html");
		}
		else {
			try {
				@SuppressWarnings("unchecked")
				List<String> cart = (List<String>) httpSession.getAttribute("Cart");
				List<String> foundBooks = bookService.retrieveBookISBNsByKeyword(keyword);
	
				if (foundBooks == null || foundBooks.isEmpty()) {
					foundBooks = bookService.getAllBookISBNs();
					errors.add("productalart", "error.search.notfound");
				}
				List<VBook> vProductList = bookService.createVBookList(foundBooks, cart);
	
				httpSession.setAttribute("ProductList", foundBooks);
				httpSession.setAttribute("ProductListView", vProductList);
			}
			catch (SQLException e) {
				log.log(Level.SEVERE, "", e);
				errors.add("productalart", "error.system,exception");
			}
			dispatcher = req.getRequestDispatcher(nextPage);
		}
		try {
			dispatcher.forward(req, res);
		}
		catch (ServletException | IOException e) {
			log.log(Level.SEVERE, "", e);
		}
	}

}
