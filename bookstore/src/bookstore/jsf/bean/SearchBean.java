package bookstore.jsf.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import bookstore.logic.BookLogic;
import bookstore.session.SessionBean;
import bookstore.vbean.VBook;

import javax.faces.event.ValueChangeEvent;


@Component("searchBean")
@Scope("request")
public class SearchBean {

	private SessionBean session;
	private BookLogic bookLogic;

	private String keyword;

	// 表示用
	private List<VBook> bookList;


	public SearchBean() {
		System.out.println("SearchBean<init>: called.");
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<VBook> getBookList() {
		List<String> foundBooks = session.getFoundBooks();
		if (foundBooks == null || foundBooks.isEmpty()) {
			foundBooks = bookLogic.getAllBookISBNs();
			session.setFoundBooks(foundBooks);
			keyword = null;
		}
		bookList = bookLogic.createVBookList(foundBooks, session.getSelectedBooks());
		System.out.println("SearchBean.getBookList: bookList.size=" + (bookList == null ? 0 : bookList.size()) + ", bookList=" + bookList);
		return bookList;
	}


	public String search() {
		System.out.println("SearchBean.search: this=" + this);

		if (session.getUid() == null) {
			return "illegalSession";
		}

		System.out.println("SearchBean.search: keyword=" + getKeyword());
		List<String> foundBooks = bookLogic.retrieveBookISBNsByKeyword(getKeyword());
		System.out.println("SearchBean.search: foundBooks.size=" + (foundBooks == null ? 0 : foundBooks.size()) + ", foundBooks=" + foundBooks);

		if (foundBooks == null || foundBooks.isEmpty()) {
			foundBooks = bookLogic.getAllBookISBNs();
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "error.search.notfound", "[error.search.notfound]です。");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
		}

		bookList = bookLogic.createVBookList(foundBooks, session.getSelectedBooks());
		System.out.println("SearchBean.search: bookList.size=" + (bookList == null ? 0 : bookList.size()) + ", bookList=" + bookList);

		session.setFoundBooks(foundBooks);

		return "gotoBookStore";
	}

	public String addToCart() {
		System.out.println("SearchBean.addToCart: this=" + this);

		if (session.getUid() == null) {
			return "illegalSession";
		}

		List<String> cart = session.getCart();
		System.out.println("SearchBean.addToCart: cart.size=" + (cart == null ? 0 : cart.size()) + ", cart=" + cart);

		List<String> foundBooks = session.getFoundBooks();
		System.out.println("SearchBean.addToCart: foundBooks.size=" + (foundBooks == null ? 0 : foundBooks.size()) + ", foundBooks=" + foundBooks);

		List<String> selectedBooks = session.getSelectedBooks();
		System.out.println("SearchBean.addToCart: selectedBooks.size=" + (selectedBooks == null ? 0 : selectedBooks.size()) + ", selectedBooks=" + selectedBooks);
		if (selectedBooks == null || selectedBooks.isEmpty()) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "error.selected.notfound", "[error.selected.notfound]です。");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
			return "gotoBookStore";
		}

		// 新ロジックではselectedBooksが表示されていないBookのselectedを保持しているため旧cartの情報を引き渡す必要はない。
		//List<String> newCart = bookLogic.createCart(foundBooks, selectedBooks, cart);
		//System.out.println("SearchAction.addToCart: newCart.size=" + (newCart == null ? 0 : newCart.size()) + ", newCart=" + newCart);

		FacesContext fc = FacesContext.getCurrentInstance();
		for (String book : selectedBooks) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "info.cart.books = " + book, "[info.cart.books] = " + book + "です。");
			fc.addMessage(null, fm);
		}

		session.setCart(selectedBooks);
		session.setFoundBooks(null);

		//bookList = bookLogic.createVBookList(null, selectedBooks);
		//System.out.println("AddToCartAction.getBookList: bookList.size=" + (bookList == null ? 0 : bookList.size()) + ", bookList=" + bookList);
		return "gotoBookStore";
	}

	public void selectValueChange(ValueChangeEvent e) {
		HtmlSelectBooleanCheckbox cb = (HtmlSelectBooleanCheckbox)e.getSource();
		String label = cb.getLabel();
		List<String> selectedBooks = session.getSelectedBooks();

		if ("true".equals(e.getNewValue().toString())) {
			if (selectedBooks.contains(label)) {
				System.out.println("SearchBean.selectValueChange: item found => skip. cb.label=" + cb.getLabel());
				return;
			}
			System.out.println("SearchBean.selectValueChange: item not found => add. cb.label=" + cb.getLabel());
			selectedBooks.add(label);
		}
		else {
			if (selectedBooks.contains(label)) {
				System.out.println("SearchBean.selectValueChange: item found => remove. cb.label=" + cb.getLabel());
				selectedBooks.remove(label);
				return;
			}
			System.out.println("SearchBean.selectValueChange: item not found => skip. cb.label=" + cb.getLabel());
		}
	}

	public void setSession(SessionBean session) {
		this.session = session;
	}
	public void setBookLogic(BookLogic bookLogic) {
		this.bookLogic = bookLogic;
	}
}
