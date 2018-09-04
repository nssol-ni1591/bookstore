package bookstore.jsf.bean;

import bookstore.logic.BookLogic;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import bookstore.logic.CustomerLogic;
import bookstore.logic.OrderLogic;
import bookstore.session.SessionBean;
import bookstore.vbean.VBook;
import bookstore.vbean.VCustomer;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


@Component("orderBean")
@Scope("request")
public class OrderBean {

	private SessionBean session;
	private BookLogic bookLogic;

	//private int total;
	private CustomerLogic customerLogic;
	private OrderLogic orderLogic;

	private VCustomer vcustomer;
	private List<VBook> bookList;

	public SessionBean getSession() {
		return session;
	}
/*
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
*/
	public List<VBook> getBookList() {
		return bookList;
	}

	public VCustomer getVcustomer() {
		return vcustomer;
	}


	public String order() {

		String uid = session.getUid();
		List<String> cart = session.getCart();
		if (session.getUid() == null) {
			return "illegalSession";
		}

		if (cart == null || cart.isEmpty()) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"error.order.nocart", "[error.order.nocart]Ç≈Ç∑ÅB");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
			return "gotoBookStore";
		}

		orderLogic.orderBooks(uid, cart);

		bookList = bookLogic.createVBookList(cart, cart);
		vcustomer = customerLogic.createVCustomer(uid);
		//VCheckout vc = bookLogic.createVCheckout(cart);
		//total = vc.getTotal();

		session.setCart(null);
		session.setFoundBooks(null);
		session.setSelectedBooks(null);
		return "gotoOrder";
	}

	public void setSession(SessionBean session) {
		this.session = session;
	}
	public void setBookLogic(BookLogic bookLogic) {
		this.bookLogic = bookLogic;
	}
	public void setCustomerLogic(CustomerLogic customerLogic) {
		this.customerLogic = customerLogic;
	}
	public void setOrderLogic(OrderLogic orderLogic) {
		this.orderLogic = orderLogic;
	}
}