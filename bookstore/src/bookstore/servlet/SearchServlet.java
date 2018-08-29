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
import bookstore.vbean.VBook;

public class SearchServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String keyword = req.getParameter("keyword");

		Map<String, String> errors = new HashMap<>();
		RequestDispatcher dispatcher;

		HttpSession httpSession = req.getSession(false);
		if (httpSession == null) {
			dispatcher = req.getRequestDispatcher("sessionError.vm");
		}
		else {
			BookLogic bookLogic = new BookLogicImpl2();
			List<String> cart = (List<String>) httpSession.getAttribute("Cart");
			List<String> foundBooks = bookLogic.retrieveBookISBNsByKeyword(keyword);

			if (foundBooks == null || foundBooks.isEmpty()) {
				foundBooks = bookLogic.getAllBookISBNs();

				errors.put("productalart", "error.search.notfound");
				req.setAttribute("errors", errors);
			}
			else {
				List<VBook> vProductList = bookLogic.createVBookList(foundBooks, cart);

				httpSession.setAttribute("ProductList", foundBooks);
				httpSession.setAttribute("ProductListView", vProductList);
			}
			dispatcher = req.getRequestDispatcher("BookStore.jsp");
		}
		dispatcher.forward(req, res);
	}
	/*
	public void setBookLogic(BookLogic bookLogic) {
		this.bookLogic = bookLogic;
	}
	*/
}