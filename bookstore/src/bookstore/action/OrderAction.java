package bookstore.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import bookstore.logic.CustomerLogic;
import bookstore.logic.OrderLogic;

public class OrderAction extends Action {

	OrderLogic orderLogic;
	CustomerLogic customerLogic;

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

		orderLogic.orderBooks(uid, cart);

		req.setAttribute("Customer", customerLogic.createVCustomer(uid));

		return (mapping.findForward("OrderSuccess"));
	}

	public void setOrderLogic(OrderLogic orderLogic) {
		this.orderLogic = orderLogic;
	}

	public void setCustomerLogic(CustomerLogic customerLogic) {
		this.customerLogic = customerLogic;
	}
}