package bookstore.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import bookstore.vbean.VBook;

/*
 * Logic LayerÇÃéQè∆Ç≈DAOÇêÿë÷Ç¶ÇÈ
 * ÅEjdbc native - bookstore.logic.wrapper.xxxxLogicWrapper
 * ÅEeclipselink - bookstore.logic.jpa.xxxxLogicWrapper
 */
public class AddToCartServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) {

		RequestDispatcher dispatcher;

		HttpSession httpSession = req.getSession(false);
		if (httpSession == null) {
			dispatcher = req.getRequestDispatcher("sessionError.html");
		}
		else {
			BookLogic bookLogic = new BookLogicWrapper();

			@SuppressWarnings("unchecked")
			List<String> cart = (List<String>)httpSession.getAttribute("Cart");
			if (cart == null) {
				cart = new ArrayList<>();
			}
			@SuppressWarnings("unchecked")
			List<String> productList = (List<String>)httpSession.getAttribute("ProductList");

			String[] selecteItemsArray = req.getParameterValues("selecteditems");
			List<String> selectedItems = null;

			if (selecteItemsArray != null && selecteItemsArray.length != 0) {
				selectedItems = Arrays.asList(selecteItemsArray);

				List<String> newCart = bookLogic.createCart(productList, selectedItems, cart);

				httpSession.setAttribute("Cart", newCart);

				List<String> productListAll = bookLogic.getAllBookISBNs();
				List<VBook> vProductList = bookLogic.createVBookList(productListAll, newCart);

				httpSession.setAttribute("ProductList", productListAll);
				httpSession.setAttribute("ProductListView", vProductList);
			}
			else {
				Messages errors = new Messages(req);
				errors.add("productalart", "error.addtocart.notselected");
			}
			dispatcher = req.getRequestDispatcher("BookStore.jsp");
		}
		try {
			dispatcher.forward(req, res);
		}
		catch (ServletException | IOException e) {
			Logger.getLogger(AddToCartServlet.class.getName()).log(Level.SEVERE, "", e);
		}
	}
}