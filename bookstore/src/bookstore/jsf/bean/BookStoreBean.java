package bookstore.jsf.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
@SessionScoped
public class BookStoreBean implements Serializable {

	private static final long serialVersionUID = -1L;

	@Inject @UsedWeld private transient BookLogic bookLogic;

	private String keyword;

	// 表示用
	private List<VBook> bookList;


	public BookStoreBean() {
		System.out.println("BookStoreBean<init>: called.");
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

		@SuppressWarnings("unchecked")
		List<String> foundBooks = (List<String>)session.getAttribute("FoundBooks");
		if (foundBooks == null || foundBooks.isEmpty()) {
			foundBooks = bookLogic.getAllBookISBNs();
			session.setAttribute("FoundBooks", foundBooks);
			keyword = null;
		}
		@SuppressWarnings("unchecked")
		List<String> selectedBooks = (List<String>)session.getAttribute("SelectedBooks");
		bookList = bookLogic.createVBookList(foundBooks, selectedBooks);
		System.out.println("BookStoreBean.getBookList: bookList.size=" + (bookList == null ? 0 : bookList.size()) + ", bookList=" + bookList);
		return bookList;
	}


	public String search() {
		System.out.println("BookStoreBean.search: this=" + this);

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(false);
		if (session == null) {
			return "sessionError.html";
		}

		System.out.println("BookStoreBean.search: keyword=" + getKeyword());
		List<String> foundBooks = bookLogic.retrieveBookISBNsByKeyword(getKeyword());
		System.out.println("BookStoreBean.search: foundBooks.size=" + (foundBooks == null ? 0 : foundBooks.size()) + ", foundBooks=" + foundBooks);

		if (foundBooks == null || foundBooks.isEmpty()) {
			foundBooks = bookLogic.getAllBookISBNs();
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "error.search.notfound", "[error.search.notfound]です。");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
		}
		@SuppressWarnings("unchecked")
		List<String> selectedBooks = (List<String>)session.getAttribute("SelectedBooks");
		bookList = bookLogic.createVBookList(foundBooks, selectedBooks);
		System.out.println("BookStoreBean.search: bookList.size=" + (bookList == null ? 0 : bookList.size()) + ", bookList=" + bookList);

		session.setAttribute("FoundBooks", foundBooks);
		return "BookStore";
	}

	public String addToCart() {
		System.out.println("BookStoreBean.addToCart: this=" + this);

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(false);
		if (session == null) {
			return "sessionError.html";
		}

		@SuppressWarnings("unchecked")
		List<String> cart = (List<String>)session.getAttribute("Cart");
		System.out.println("BookStoreBean.addToCart: cart.size=" + (cart == null ? 0 : cart.size()) + ", cart=" + cart);

		@SuppressWarnings("unchecked")
		List<String> foundBooks = (List<String>)session.getAttribute("FoundBooks");
		System.out.println("BookStoreBean.addToCart: foundBooks.size=" + (foundBooks == null ? 0 : foundBooks.size()) + ", foundBooks=" + foundBooks);

		@SuppressWarnings("unchecked")
		List<String> selectedBooks = (List<String>)session.getAttribute("SelectedBooks");
		System.out.println("BookStoreBean.addToCart: selectedBooks.size=" + (selectedBooks == null ? 0 : selectedBooks.size()) + ", selectedBooks=" + selectedBooks);
		if (selectedBooks == null || selectedBooks.isEmpty()) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "error.selected.notfound", "[error.selected.notfound]です。");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
			return "BookStore";
		}

		// 新ロジックではselectedBooksが表示されていないBookのselectedを保持しているため旧cartの情報を引き渡す必要はない。
		//List<String> newCart = bookLogic.createCart(foundBooks, selectedBooks, cart);
		//System.out.println("SearchAction.addToCart: newCart.size=" + (newCart == null ? 0 : newCart.size()) + ", newCart=" + newCart);

		FacesContext fc = FacesContext.getCurrentInstance();
		for (String book : selectedBooks) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "info.cart.books = " + book, "[info.cart.books] = " + book + "です。");
			fc.addMessage(null, fm);
		}

		session.setAttribute("Cart", selectedBooks);
		session.setAttribute("FoundBooks", null);

		//bookList = bookLogic.createVBookList(null, selectedBooks);
		//System.out.println("AddToCartAction.getBookList: bookList.size=" + (bookList == null ? 0 : bookList.size()) + ", bookList=" + bookList);
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
		session.removeAttribute("SelectedBooks");

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
		List<String> selectedBooks = (List<String>)session.getAttribute("SelectedBooks");
		if (selectedBooks == null) {
			System.out.println("BookStoreBean: created session");
			selectedBooks = new ArrayList<>();
		}

		if ("true".equals(e.getNewValue().toString())) {
			if (selectedBooks.contains(label)) {
				System.out.println("BookStoreBean.selectValueChange: item found => skip. cb.label=" + cb.getLabel());
				return;
			}
			System.out.println("BookStoreBean.selectValueChange: item not found => add. cb.label=" + cb.getLabel());
			selectedBooks.add(label);
			session.setAttribute("SelectedBooks", selectedBooks);
		}
		else {
			if (selectedBooks.contains(label)) {
				System.out.println("BookStoreBean.selectValueChange: item found => remove. cb.label=" + cb.getLabel());
				selectedBooks.remove(label);
				session.setAttribute("SelectedBooks", selectedBooks);
				return;
			}
			System.out.println("BookStoreBean.selectValueChange: item not found => skip. cb.label=" + cb.getLabel());
		}
	}

}
