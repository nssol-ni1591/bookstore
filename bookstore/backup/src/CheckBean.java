package bookstore.jsf.bean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import bookstore.annotation.UsedWeld;
import bookstore.logic.BookLogic;
import bookstore.vbean.VBook;
import bookstore.vbean.VCheckout;

@Named
@RequestScoped
public class CheckBean {

	@Inject @UsedWeld private BookLogic bookLogic;

	// ï\é¶
	private VCheckout itemsToBuy;
	private int total;


	public CheckBean() {
		System.out.println("CheckBean<init>: called.");
	}

	public VCheckout getItemsToBuy() {
		return itemsToBuy;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String checkCart() {
		System.out.println("CheckBean.checkCart: this=" + this);

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(false);
		List<String> selectedItems = (List<String>)session.getAttribute("Cart");
		if (selectedItems == null || selectedItems.isEmpty()) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"error.cart.noselected", "[error.cart.noselected]Ç≈Ç∑ÅB");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
			return "BookStore";
		}

		//session.setAttribute("ItemsToBuy", bookLogic.createVCheckout(selectedItems));
		itemsToBuy = bookLogic.createVCheckout(selectedItems);
		return "Check";
	}

}
