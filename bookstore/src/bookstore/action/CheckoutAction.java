package bookstore.action;

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

import bookstore.logic.BookLogic;

public class CheckoutAction extends Action {

	BookLogic bookLogic;

	public ActionForward execute(ActionMapping mapping
			, ActionForm form
			, HttpServletRequest req
			, HttpServletResponse res) {

		HttpSession httpSession = req.getSession(false);
		if (httpSession == null) {
			return (mapping.findForward("illegalSession"));
		}

		@SuppressWarnings("unchecked")
		List<String> selectedItems = (List<String>) httpSession.getAttribute("Cart");

		if (selectedItems == null || selectedItems.size() == 0) {

			ActionMessages errors = new ActionMessages();

			errors.add("productalart", new ActionMessage("error.checkout.noselected"));
			saveMessages(req, errors);
			return (mapping.findForward("illegalCheckout"));
		}

		httpSession.setAttribute("ItemsToBuy", bookLogic.createVCheckout(selectedItems));

		return (mapping.findForward("ToCheck"));
	}

	public void setBookLogic(BookLogic bookLogic) {
		this.bookLogic = bookLogic;
	}
}
