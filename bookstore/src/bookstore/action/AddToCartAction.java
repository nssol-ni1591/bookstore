package bookstore.action;

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
import bookstore.logic.BookLogic;
import bookstore.vbean.VBook;

public class AddToCartAction extends Action {

	private BookLogic bookLogic;

	@Override
	public ActionForward execute(ActionMapping mapping
			, ActionForm form
			, HttpServletRequest req
			, HttpServletResponse res) {

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

		List<String> newCart = bookLogic.createCart(productList, selectedItems, cart);

		httpSession.setAttribute("Cart", newCart);

		List<String> productListAll = bookLogic.getAllBookISBNs();
		List<VBook> vProductList = bookLogic.createVBookList(productListAll, newCart);

		httpSession.setAttribute("ProductList", productListAll);
		httpSession.setAttribute("ProductListView", vProductList);

		return (mapping.findForward("Continue"));
	}

	public void setBookLogic(BookLogic bookLogic) {
		this.bookLogic = bookLogic;
	}
}