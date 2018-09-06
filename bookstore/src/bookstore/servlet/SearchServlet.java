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
import bookstore.logic.pojo.BookLogicWrapper;
import bookstore.util.Messages;
import bookstore.vbean.VBook;

/*
 * Logic LayerÇÃéQè∆Ç≈DAOÇêÿë÷Ç¶ÇÈ
 * ÅEjdbc native - bookstore.logic.wrapper.xxxxLogicWrapper
 * ÅEeclipselink - bookstore.logic.jpa.xxxxLogicWrapper
 */
public class SearchServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

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
			BookLogic bookLogic = new BookLogicWrapper();
			@SuppressWarnings("unchecked")
			List<String> cart = (List<String>) httpSession.getAttribute("Cart");
			List<String> foundBooks = bookLogic.retrieveBookISBNsByKeyword(keyword);

			if (foundBooks == null || foundBooks.isEmpty()) {
				foundBooks = bookLogic.getAllBookISBNs();

				errors.add("productalart", "error.search.notfound");
			}
			List<VBook> vProductList = bookLogic.createVBookList(foundBooks, cart);

			httpSession.setAttribute("ProductList", foundBooks);
			httpSession.setAttribute("ProductListView", vProductList);

			dispatcher = req.getRequestDispatcher("BookStore.jsp");
		}
		try {
			dispatcher.forward(req, res);
		}
		catch (ServletException | IOException e) {
			Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, "", e);
		}
	}
}