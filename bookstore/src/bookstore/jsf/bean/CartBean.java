package bookstore.jsf.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import bookstore.logic.BookLogic;
import bookstore.session.SessionBean;
import bookstore.vbean.VBook;
import bookstore.vbean.VCheckout;


@Component("cartBean")
@Scope("request")
public class CartBean {

	private SessionBean session;

	private BookLogic bookLogic;

	// ï\é¶
	private List<VBook> bookList;
	private int total;


	public CartBean() {
		System.out.println("CartAction<init>: called.");
	}

	public SessionBean getSession() {
		return session;
	}

	public List<VBook> getBookList() {
		//List<String> cart = session.getCart();
		//List<VBook> bookList = bookLogic.createVBookList(cart, cart);
		return bookList;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String checkCart() {
		System.out.println("CartAction.checkCart: this=" + this);

		List<String> cart = session.getCart();
		if (cart == null || cart.isEmpty()) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"error.cart.noselected", "[error.cart.noselected]Ç≈Ç∑ÅB");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
			return "gotoBookStore";
		}

		bookList = bookLogic.createVBookList(cart, cart);
		VCheckout vc = bookLogic.createVCheckout(cart);
		total = vc.getTotal();

		return "gotoCart";
	}

	public String clearCart() {
		System.out.println("CartAction.clearCart: this=" + this);

		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"info.cart.clear", "[info.cart.clear]Ç≈Ç∑ÅB");
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, fm);

		session.setUid(session.getUid());
		return "gotoBookStore";
	}

	public void setSession(SessionBean session) {
		this.session = session;
	}
	public void setBookLogic(BookLogic bookLogic) {
		this.bookLogic = bookLogic;
	}
}
