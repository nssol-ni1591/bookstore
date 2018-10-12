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

import bookstore.service.BookService;
import bookstore.service.classic.BookServiceWrapper;
import bookstore.util.Messages;
import bookstore.vbean.VBook;

/*
 * Service LayerÇÃéQè∆Ç≈DAOÇêÿë÷Ç¶ÇÈ
 * ÅEjdbc native - bookstore.service.wrapper.xxxxServiceWrapper
 * ÅEeclipselink - bookstore.service.jpa.xxxxServiceWrapper
 */
public class SearchServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(SearchServlet.class.getName());

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) {

		String keyword = req.getParameter("keyword");

		Messages errors = new Messages(req);
		RequestDispatcher dispatcher;

		HttpSession httpSession = req.getSession(false);
		if (httpSession == null) {
			dispatcher = req.getRequestDispatcher("sessionError.html");
		}
		else {
			try {
				BookService bookService = new BookServiceWrapper();
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
			dispatcher = req.getRequestDispatcher("BookStore.jsp");
		}
		try {
			dispatcher.forward(req, res);
		}
		catch (ServletException | IOException e) {
			log.log(Level.SEVERE, "", e);
		}
	}
}