package bookstore.jsf.bean;

import bookstore.annotation.UsedWeld;
import bookstore.logic.BookLogic;

import java.util.List;

import bookstore.logic.CustomerLogic;
import bookstore.logic.OrderLogic;
import bookstore.vbean.VCheckout;
import bookstore.vbean.VCustomer;

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

	@Inject @UsedWeld private BookLogic bookLogic;
	@Inject @UsedWeld private CustomerLogic customerLogic;
	@Inject @UsedWeld private OrderLogic orderLogic;

	private VCheckout itemsToBuy;
	private VCustomer vcustomer;


	public VCheckout getItemsToBuy() {
		return itemsToBuy;
	}

	public VCustomer getVcustomer() {
		/*
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(false);
		String uid = (String) session.getAttribute("Login");
		vcustomer = customerLogic.createVCustomer(uid);
		*/
		return vcustomer;
	}

	public String order() {

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(false);
		if (session == null) {
			return "sessionError.html";
		}

		String uid = (String) session.getAttribute("Login");
		if (uid == null) {
			return "sessionError.html";
		}

		@SuppressWarnings("unchecked")
		List<String> cart = (List<String>) session.getAttribute("Cart");
		orderLogic.orderBooks(uid, cart);

		vcustomer = customerLogic.createVCustomer(uid);
		itemsToBuy = bookLogic.createVCheckout(cart);

		session.setAttribute("Cart", null);
		session.setAttribute("FoundBooks", null);
		session.setAttribute("SelectedBooks", null);
		return "Order";
	}

	public String checkOrder() {
		System.out.println("OrderBean.checkOrder: this=" + this);

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(false);
		if (session == null) {
			return "sessionError.html";
		}

		@SuppressWarnings("unchecked")
		List<String> selectedItems = (List<String>)session.getAttribute("Cart");
		if (selectedItems == null || selectedItems.isEmpty()) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"error.cart.noselected", "[error.cart.noselected]Ç≈Ç∑ÅB");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
			return "BookStore";
		}

		itemsToBuy = bookLogic.createVCheckout(selectedItems);
		return "Check";
	}

}