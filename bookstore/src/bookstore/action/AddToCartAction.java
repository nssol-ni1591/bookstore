package bookstore.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import bookstore.action.bean.AddToCartActionFormBean;
import bookstore.service.BookService;
import bookstore.vbean.VBook;

public class AddToCartAction extends Action {

	private BookService bookService;

	@Override
	public ActionForward execute(ActionMapping mapping
			, ActionForm form
			, HttpServletRequest req
			, HttpServletResponse res) throws SQLException {

		HttpSession httpSession = req.getSession(false);
		if (httpSession == null) {
			return (mapping.findForward("illegalSession"));
		}

		@SuppressWarnings("unchecked")
		List<String> cart = (List<String>)httpSession.getAttribute("Cart");
		if (cart == null) {
			cart = new ArrayList<>();
		}

		AddToCartActionFormBean atcafb = (AddToCartActionFormBean) form;

		@SuppressWarnings("unchecked")
		List<String> productList = (List<String>)httpSession.getAttribute("ProductList");

		String[] selecteItemsArray = atcafb.getSelecteditems();
		List<String> selectedItems = null;

		if (selecteItemsArray != null && selecteItemsArray.length != 0) {
			selectedItems = Arrays.asList(atcafb.getSelecteditems());
		}

		List<String> newCart = bookService.createCart(productList, selectedItems, cart);

		httpSession.setAttribute("Cart", newCart);

		List<String> productListAll = bookService.getAllBookISBNs();
		List<VBook> vProductList = bookService.createVBookList(productListAll, newCart);

		httpSession.setAttribute("ProductList", productListAll);
		httpSession.setAttribute("ProductListView", vProductList);

		return (mapping.findForward("Continue"));
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}
}