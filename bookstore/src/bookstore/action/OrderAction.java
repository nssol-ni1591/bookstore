package bookstore.action;

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
import bookstore.service.CustomerService;
import bookstore.service.OrderService;

public class OrderAction extends Action {

	private OrderService orderLogic;
	private CustomerService customerLogic;
	@Log private static Logger log;

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
		catch (Exception e) {
			log.log(Level.SEVERE, "", e);

			ActionMessages errors = new ActionMessages();
			errors.add("orderalert", new ActionMessage("error.system.exception"));
			saveMessages(req, errors);
			return (mapping.findForward("OrderFailed"));
		}
	}

	public void setOrderLogic(OrderService orderLogic) {
		this.orderLogic = orderLogic;
	}

	public void setCustomerLogic(CustomerService customerLogic) {
		this.customerLogic = customerLogic;
	}
}