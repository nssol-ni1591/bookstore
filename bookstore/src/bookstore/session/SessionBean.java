package bookstore.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class SessionBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String uid;
	private List<String> cart;
	private List<String> foundBooks;
	private List<String> selectedBooks;


	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
		this.cart = new ArrayList<String>();
		this.foundBooks  = new ArrayList<String>();
		this.selectedBooks = new ArrayList<String>();
	}
	public List<String> getCart() {
		return cart;
	}
	public void setCart(List<String> cart) {
		this.cart = cart;
	}
	public List<String> getFoundBooks() {
		return foundBooks;
	}
	public void setFoundBooks(List<String> foundBooks) {
		this.foundBooks = foundBooks;
	}
	public List<String> getSelectedBooks() {
		return selectedBooks;
	}
	public void setSelectedBooks(List<String> selectedBooks) {
		this.selectedBooks = selectedBooks;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[uid=").append(uid);
		sb.append(",cart=").append(cart);
		sb.append(",foundBooks=").append(foundBooks);
		sb.append(",selectedBooks=").append(selectedBooks);
		sb.append("]");
		return sb.toString();
	}
}
