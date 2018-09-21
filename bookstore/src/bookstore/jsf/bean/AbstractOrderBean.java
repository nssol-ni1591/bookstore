package bookstore.jsf.bean;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import bookstore.logic.BookLogic;
import bookstore.logic.CustomerLogic;
import bookstore.logic.OrderLogic;
import bookstore.util.Messages;
import bookstore.vbean.VCheckout;
import bookstore.vbean.VCustomer;
import bookstore.vbean.VOrder;
import bookstore.vbean.VOrderDetail;

public abstract class AbstractOrderBean {

	private static final String SESSION_ERROR = "sessionError.html";
	private static final String THIS_0 = "this={0}";

	@Inject private Logger log;

	protected abstract BookLogic getBookLogic();
	protected abstract CustomerLogic getCustomerLogic();
	protected abstract OrderLogic getOrderLogic();

	protected abstract String getBookStorePage();
	protected abstract String getCheckPage();
	protected abstract String getOrderPage();
	protected abstract String getOrderListPage();

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

	public String order() /*throws SQLException*/ {
		log.log(Level.INFO, THIS_0, this);

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(false);
		if (session == null) {
			return SESSION_ERROR;
		}

		String uid = (String) session.getAttribute("Login");
		if (uid == null) {
			return SESSION_ERROR;
		}

		BookLogic bookLogic = getBookLogic();
		CustomerLogic customerLogic = getCustomerLogic();
		OrderLogic orderLogic = getOrderLogic();

		@SuppressWarnings("unchecked")
		List<String> cart = (List<String>) session.getAttribute("Cart");

		try {
			orderLogic.orderBooks(uid, cart);
	
			vcustomer = customerLogic.createVCustomer(uid);
			//itemsToBuy = bookLogic.createVCheckout(cart);
	
			session.setAttribute("Cart", null);
		}
		catch (Exception e) {
			log.log(Level.SEVERE, "", e);
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR
					, Messages.getMessage("error.system.exception")
					, "[error.system.exception]Ç≈Ç∑ÅB");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);

			return getCheckPage();
		}
		finally {
			try {
				itemsToBuy = bookLogic.createVCheckout(cart);
			}
			catch (SQLException e) {
				// nothing to do
			}
		}
		return getOrderPage();
	}

	public String checkOrder() throws SQLException {
		log.log(Level.INFO, THIS_0, this);

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(false);
		if (session == null) {
			return SESSION_ERROR;
		}

		@SuppressWarnings("unchecked")
		List<String> cart = (List<String>)session.getAttribute("Cart");
		log.log(Level.INFO, "cart={0}", cart);
		if (cart == null || cart.isEmpty()) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR
					, Messages.getMessage("error.cart.noselected")
					, "[error.cart.noselected]Ç≈Ç∑ÅB");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
			return getBookStorePage();
		}

		BookLogic bookLogic = getBookLogic();
		itemsToBuy = bookLogic.createVCheckout(cart);
		log.log(Level.INFO, "checkPage={0}", getCheckPage());
		return getCheckPage();
	}

	public String listOrders() throws SQLException {
		log.log(Level.INFO, THIS_0, this);

		OrderLogic orderLogic = getOrderLogic();
		orders = orderLogic.listOrders(null);
		details = orderLogic.listOrderDetails(null);

		return getOrderListPage();
	}

}