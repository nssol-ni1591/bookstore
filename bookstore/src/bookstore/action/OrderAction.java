package bookstore.action;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import bookstore.annotation.Log;
import bookstore.logic.CustomerLogic;
import bookstore.logic.OrderLogic;

public class OrderAction extends Action {

	private OrderLogic orderLogic;
	private CustomerLogic customerLogic;
	@Log Logger log;

	@Override
	public ActionForward execute(ActionMapping mapping
			, ActionForm form
			, HttpServletRequest req
			, HttpServletResponse res) {

		HttpSession httpSession = req.getSession(false);
		if (httpSession == null) {
			return (mapping.findForward("illegalSession"));
		}

		String uid = (String) httpSession.getAttribute("Login");
		@SuppressWarnings("unchecked")
		List<String> cart = (List<String>)httpSession.getAttribute("Cart");

		try {
			orderLogic.orderBooks(uid, cart);
			req.setAttribute("Customer", customerLogic.createVCustomer(uid));
			return (mapping.findForward("OrderSuccess"));
		}
		catch (SQLException e) {
			log.log(Level.SEVERE, "", e);

			ActionMessages errors = new ActionMessages();
			errors.add("orderalert", new ActionMessage("error.system.exception"));
			saveMessages(req, errors);
			return (mapping.findForward("OrderFailed"));
		}
	}

	public void setOrderLogic(OrderLogic orderLogic) {
		this.orderLogic = orderLogic;
	}

	public void setCustomerLogic(CustomerLogic customerLogic) {
		this.customerLogic = customerLogic;
	}
}