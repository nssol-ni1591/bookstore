package bookstore.servlet3.jpa;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bookstore.annotation.UsedWeld;
import bookstore.service.BookService;
import bookstore.util.Messages;
import bookstore.vbean.VBook;

/*
 * ServletはScopeアノテーションを付加するとCDI管理下になる
 * @Injectの@Qualifierでservice層を切替える
 */
@WebServlet(urlPatterns="/AddToCartServlet3")
@RequestScoped
public class AddToCartServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Inject @UsedWeld private BookService bookService;
	@Inject private Logger log;


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

				//log.log(Level.INFO, "productList.size={0}, productList={1}"
				//		, new Object[] { productList.size(), productList })
				//log.log(Level.INFO, "selectedItems.size={0}, selectedItems={1}"
				//		, new Object[] { selectedItems.size(), selectedItems })
				//log.log(Level.INFO, "cart.size={0}, cart={1}"
				//		, new Object[] { cart.size(), cart })

				List<String> newCart = bookService.createCart(productList, selectedItems, cart);
				//log.log(Level.INFO, "newCart.size={0}, newCart={1}"
				//		, new Object[] { newCart.size(), newCart })

				httpSession.setAttribute("Cart", newCart);

				try {
					List<String> productListAll = bookService.getAllBookISBNs();
					List<VBook> vProductList = bookService.createVBookList(productListAll, newCart);
	
					httpSession.setAttribute("ProductList", productListAll);
					httpSession.setAttribute("ProductListView", vProductList);
				}
				catch (SQLException e) {
					log.log(Level.SEVERE, "", e);
					Messages errors = new Messages(req);
					errors.add("productalart", "error.system.exception");
				}
			}
			else {
				Messages errors = new Messages(req);
				errors.add("productalart", "error.addtocart.notselected");
			}
			dispatcher = req.getRequestDispatcher("BookStore3.jsp");
		}
		try {
			dispatcher.forward(req, res);
		}
		catch (ServletException | IOException e) {
			log.log(Level.SEVERE, "", e);
		}
	}
}