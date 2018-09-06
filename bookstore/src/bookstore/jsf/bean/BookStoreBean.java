package bookstore.jsf.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import bookstore.annotation.UsedWeld;
import bookstore.logic.BookLogic;
import bookstore.vbean.VBook;

import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class BookStoreBean implements Serializable {

	private static final long serialVersionUID = -1L;

	@Inject @UsedWeld private BookLogic bookLogic;

	private String keyword;

	// 表示用
	private List<VBook> bookList;

	//private List<String> productList;
	//private List<VBook> productListView;

	private static final boolean DEBUG_DATA = true;
	private static final boolean DEBUG_EVENT = true;


	public BookStoreBean() {
		System.out.println("BookStoreBean<init>: this=" + this);
	}

	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<VBook> getBookList() {

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(false);
		if (session == null) {
			return new ArrayList<>();
		}

		bookList = (List<VBook>)session.getAttribute("ProductListView");
		if (bookList != null) {
			if (DEBUG_DATA)
				System.out.println("BookStoreBean.getBookList(1): bookList.size=" + bookList.size() + ", bookList=" + bookList);
			return bookList;
		}

		List<String> productList = (List<String>)session.getAttribute("ProductList");
		if (productList == null || productList.isEmpty()) {
			productList = bookLogic.getAllBookISBNs();
			session.setAttribute("ProductList", productList);
			keyword = null;
		}
		bookList = bookLogic.createVBookList(productList, null);
		if (DEBUG_DATA)
			System.out.println("BookStoreBean.getBookList(2): bookList.size=" + bookList.size() + ", bookList=" + bookList);
		return bookList;
	}

	public String search() {
		System.out.println("BookStoreBean.search: this=" + this);

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(false);
		if (session == null) {
			return "sessionError.html";
		}

		if (DEBUG_DATA)
			System.out.println("BookStoreBean.search: keyword=" + getKeyword());

		List<String> foundBooks = bookLogic.retrieveBookISBNsByKeyword(getKeyword());
		if (foundBooks == null || foundBooks.isEmpty()) {
			foundBooks = bookLogic.getAllBookISBNs();
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "error.search.notfound", "[error.search.notfound]です。");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
		}
		@SuppressWarnings("unchecked")
		List<String> cart = (List<String>) session.getAttribute("Cart");
		if (DEBUG_DATA)
			System.out.println("BookStoreBean.search: cart.size=" + (cart == null ? 0 : cart.size()) + ", cart=" + cart);

		List<VBook> productListView = bookLogic.createVBookList(foundBooks, cart);
		if (DEBUG_DATA)
			System.out.println("BookStoreBean.search: productListView.size=" + (productListView == null ? 0 : productListView.size()) + ", productListView=" + productListView);

		session.setAttribute("ProductList", foundBooks);
		session.setAttribute("ProductListView", productListView);
		return "BookStore";
	}

	public String addToCart() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(false);
		if (session == null) {
			return "sessionError.html";
		}
		System.out.println("BookStoreBean.addToCart: this=" + this + ", session=" + session);

		@SuppressWarnings("unchecked")
		List<String> cart = (List<String>)session.getAttribute("Cart");
		if (cart == null) {
			cart = new ArrayList<>();
		}
		if (DEBUG_DATA)
			System.out.println("BookStoreBean.addToCart: cart.size=" + cart.size() + ", cart=" + cart);

		@SuppressWarnings("unchecked")
		List<String> productList = (List<String>)session.getAttribute("ProductList");
		if (DEBUG_DATA)
			System.out.println("BookStoreBean.addToCart: productList.size=" + productList.size() + ", productList=" + productList);

		@SuppressWarnings("unchecked")
		List<String> selectedItems = (List<String>)session.getAttribute("selectedItems");
		if (DEBUG_DATA)
			System.out.println("BookStoreBean.addToCart: selectedItems.size=" + (selectedItems == null ? 0 : selectedItems.size()) + ", selectedItems=" + selectedItems);
		if (selectedItems == null || selectedItems.isEmpty()) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "error.selected.notfound", "[error.selected.notfound]です。");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
			return "BookStore";
		}

		List<String> newCart = bookLogic.createCart(productList, selectedItems, cart);
		session.setAttribute("Cart", newCart);
		if (DEBUG_DATA)
			System.out.println("BookStoreBean.addToCart: newCart.size=" + newCart.size() + ", newCart=" + newCart);

		FacesContext fc = FacesContext.getCurrentInstance();
		for (String book : newCart) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "info.cart.books = " + book, "[info.cart.books] = " + book + "です。");
			fc.addMessage(null, fm);
		}

		List<String> productListAll = bookLogic.getAllBookISBNs();
		List<VBook> productListView = bookLogic.createVBookList(productListAll, newCart);

		productListView.stream().forEach(book -> {
			System.out.println(String.format("isbn=%s, title=%s, selected=%s", book.getIsbn(), book.getTitle(), book.isSelected()));
		});

		session.setAttribute("ProductList", productListAll);
		session.setAttribute("ProductListView", productListView);

		return "BookStore";
	}

	public String clearCart() {
		System.out.println("BookStoreBean.clearCart: this=" + this);

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(false);
		if (session == null) {
			return "sessionError.html";
		}

		session.removeAttribute("Cart");
		session.removeAttribute("ProductListView");
		session.removeAttribute("selectedItems");

		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"info.cart.clear", "[info.cart.clear]です。");
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, fm);
		return "BookStore";
	}

	public void selectValueChange(ValueChangeEvent e) {
		System.out.println("BookStoreBean.selectValueChange: this=" + this);

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(false);
		if (session == null) {
			return;
		}

		HtmlSelectBooleanCheckbox cb = (HtmlSelectBooleanCheckbox)e.getSource();
		String label = cb.getLabel();
		@SuppressWarnings("unchecked")
		List<String> selectedItems = (List<String>)session.getAttribute("selectedItems");
		if (selectedItems == null) {
			if (DEBUG_EVENT)
				System.out.println("BookStoreBean: created selectedItems");
			selectedItems = new ArrayList<>();
		}

		if ("true".equals(e.getNewValue().toString())) {
			if (selectedItems.contains(label)) {
				if (DEBUG_EVENT)
					System.out.println("BookStoreBean.selectValueChange: item found => skip. cb.label=" + cb.getLabel());
				return;
			}
			if (DEBUG_EVENT)
				System.out.println("BookStoreBean.selectValueChange: item not found => add. cb.label=" + cb.getLabel());
			selectedItems.add(label);
			session.setAttribute("selectedItems", selectedItems);
		}
		else {
			if (selectedItems.contains(label)) {
				if (DEBUG_EVENT)
					System.out.println("BookStoreBean.selectValueChange: item found => remove. cb.label=" + cb.getLabel());
				selectedItems.remove(label);
				session.setAttribute("selectedItems", selectedItems);
				return;
			}
			if (DEBUG_EVENT)
				System.out.println("BookStoreBean.selectValueChange: item not found => skip. cb.label=" + cb.getLabel());
		}
	}

}
