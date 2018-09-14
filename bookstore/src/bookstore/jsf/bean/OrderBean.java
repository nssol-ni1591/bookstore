package bookstore.jsf.bean;

import bookstore.annotation.UsedWeld;
import bookstore.logic.BookLogic;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import bookstore.logic.CustomerLogic;
import bookstore.logic.OrderLogic;
import bookstore.util.Messages;
import bookstore.vbean.VCheckout;
import bookstore.vbean.VCustomer;
import bookstore.vbean.VOrder;
import bookstore.vbean.VOrderDetail;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class OrderBean {

	private static final String SESSION_ERROR = "sessionError.html";

	@Inject @UsedWeld private BookLogic bookLogic;
	@Inject @UsedWeld private CustomerLogic customerLogic;
	@Inject @UsedWeld private OrderLogic orderLogic;
	@Inject private Logger log;

	private VCheckout itemsToBuy;
	private VCustomer vcustomer;

	private List<VOrder> orders;
	private List<VOrderDetail> details;

	public VCheckout getItemsToBuy() {
		return itemsToBuy;
	}
	public VCustomer getVcustomer() {
		return vcustomer;
	}

	public List<VOrder> getOrders() {
		return orders;
	}
	public List<VOrderDetail> getDetails() {
		return details;
	}

	public String order() {
		log.log(Level.INFO, "this={0}", this);

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(false);
		if (session == null) {
			return SESSION_ERROR;
		}

		String uid = (String) session.getAttribute("Login");
		if (uid == null) {
			return SESSION_ERROR;
		}

		@SuppressWarnings("unchecked")
		List<String> cart = (List<String>) session.getAttribute("Cart");
		orderLogic.orderBooks(uid, cart);

		vcustomer = customerLogic.createVCustomer(uid);
		itemsToBuy = bookLogic.createVCheckout(cart);

		session.setAttribute("Cart", null);
		return "Order";
	}

	public String checkOrder() {
		log.log(Level.INFO, "this={0}", this);

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(false);
		if (session == null) {
			return SESSION_ERROR;
		}

		@SuppressWarnings("unchecked")
		List<String> cart = (List<String>)session.getAttribute("Cart");
		if (cart == null || cart.isEmpty()) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR
					, Messages.getMessage("error.cart.noselected")
					, "[error.cart.noselected]�ł��B");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
			return "BookStore";
		}

		itemsToBuy = bookLogic.createVCheckout(cart);
		return "Check";
	}

	public String listOrders() {
		log.log(Level.INFO, "this={0}", this);

		orders = orderLogic.listOrders(null);
		details = orderLogic.listOrderDetails(null);

		return "OrderList";
	}

}