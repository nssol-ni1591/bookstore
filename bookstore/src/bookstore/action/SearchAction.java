package bookstore.action;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import bookstore.action.bean.SearchActionFormBean;
import bookstore.service.BookService;
import bookstore.vbean.VBook;

public class SearchAction extends Action {

	private BookService bookLogic;

	@Override
	public ActionForward execute(ActionMapping mapping
			, ActionForm form
			, HttpServletRequest req
			, HttpServletResponse res) throws SQLException {

		HttpSession httpSession = req.getSession(false);
		if (httpSession == null) {
			return mapping.findForward("illegalSession");
		}

		ActionMessages errors;

		@SuppressWarnings("unchecked")
		List<String> cart = (List<String>) httpSession.getAttribute("Cart");

		SearchActionFormBean safb = (SearchActionFormBean) form;

		List<String> foundBooks = bookLogic.retrieveBookISBNsByKeyword(safb.getKeyword());

		if (foundBooks == null || foundBooks.isEmpty()) {

			foundBooks = bookLogic.getAllBookISBNs();

			errors = new ActionMessages();
			errors.add("productalart", new ActionMessage("error.search.notfound"));
			saveMessages(req, errors);
		}

		List<VBook> vProductList = bookLogic.createVBookList(foundBooks, cart);

		httpSession.setAttribute("ProductList", foundBooks);
		httpSession.setAttribute("ProductListView", vProductList);

		return (mapping.findForward("SearchSuccess"));
	}

	public void setBookLogic(BookService bookLogic) {
		this.bookLogic = bookLogic;
	}
}