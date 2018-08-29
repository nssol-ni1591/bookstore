package bookstore.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bookstore.logic.BookLogic;
import bookstore.logic.BookLogicImpl2;
import bookstore.vbean.VBook;

public class AddToCartServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		RequestDispatcher dispatcher;

		HttpSession httpSession = req.getSession(false);
		if (httpSession == null) {
			dispatcher = req.getRequestDispatcher("sessionError.vm");
		}
		else {
			BookLogic bookLogic = new BookLogicImpl2();

			List<String> cart = (List<String>)httpSession.getAttribute("Cart");
			if (cart == null) {
				cart = new ArrayList<>();
			}
			List<String> productList = (List<String>)httpSession.getAttribute("ProductList");

			String[] selecteItemsArray = req.getParameterValues("selecteditems");
			List<String> selectedItems = null;

			if (selecteItemsArray != null && selecteItemsArray.length != 0) {
				selectedItems = Arrays.asList(selecteItemsArray);
			}

			List<String> newCart = bookLogic.createCart(productList, selectedItems, cart);

			httpSession.setAttribute("Cart", newCart);

			List<String> productListAll = bookLogic.getAllBookISBNs();
			List<VBook> vProductList = bookLogic.createVBookList(productListAll, newCart);

			httpSession.setAttribute("ProductList", productListAll);
			httpSession.setAttribute("ProductListView", vProductList);
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