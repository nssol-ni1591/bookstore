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
import bookstore.util.Messages;
import bookstore.vbean.VBook;

import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@SessionScoped
public class BookStoreBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject @UsedWeld private BookLogic bookLogic;

	private String keyword;

	private List<String> productList;
	private List<VBook> productListView;
	private List<String> selectedItems;


	private static final boolean DEBUG_DATA = true;
	private static final boolean DEBUG_DATA_DETAIL = false;
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

	public List<String> getProductList() {
		return productList;
	}

	public List<VBook> getProductListView() {
		return productListView;
	}

	public List<VBook> getBookList() {
		if (productListView != null) {
			//i-f (DEBUG_DATA)
			//	System.out.println("BookStoreBean.getBookList(1): bookList.size=" + productListView.size() + ", bookList=" + productListView)
			return productListView;
		}

		// LoginBeanから遷移してきた場合はproductListViewがnullになる
		if (productList == null || productList.isEmpty()) {
			productList = bookLogic.getAllBookISBNs();
		}
		productListView = bookLogic.createVBookList(productList, null);
		if (DEBUG_DATA)
			System.out.println("BookStoreBean.getBookList(2): bookList.size=" + productListView.size() + ", bookList=" + productListView);
		return productListView;
	}

	public String search() {
		System.out.println("BookStoreBean.search: this=" + this);

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(false);
		if (session == null) {
			return "sessionError.html";
		}

		if (DEBUG_DATA)
			System.out.println("BookStoreBean.search: keyword=" + keyword);

		List<String> foundBooks = bookLogic.retrieveBookISBNsByKeyword(keyword);
		if (foundBooks == null || foundBooks.isEmpty()) {
			foundBooks = bookLogic.getAllBookISBNs();
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, Messages.getMessage("error.search.notfound"), "[error.search.notfound]です。");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
		}
		@SuppressWarnings("unchecked")
		List<String> cart = (List<String>) session.getAttribute("Cart");
		if (DEBUG_DATA)
			System.out.println("BookStoreBean.search: cart.size=" + (cart == null ? 0 : cart.size()) + ", cart=" + cart);

		productListView = bookLogic.createVBookList(foundBooks, cart);
		if (DEBUG_DATA)
			System.out.println("BookStoreBean.search: productListView.size=" + (productListView == null ? 0 : productListView.size()) + ", productListView=" + productListView);

		// 検索結果から外れたselected状態にあったItemが、次のaddToCartでカートに追加されるのを防ぐため、addToCartの対象を絞り込む
		productList = foundBooks;

		// @SessionScopeにしたので初期化する必要があり
		keyword = "";
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
		if (cart == null) {
			cart = new ArrayList<>();
		}
		if (DEBUG_DATA)
			System.out.println("BookStoreBean.addToCart: cart.size=" + cart.size() + ", cart=" + cart);

		if (DEBUG_DATA)
			System.out.println("BookStoreBean.addToCart: productList.size=" + productList.size() + ", productList=" + productList);

		if (DEBUG_DATA)
			System.out.println("BookStoreBean.addToCart: selectedItems.size=" + (selectedItems == null ? 0 : selectedItems.size()) + ", selectedItems=" + selectedItems);
		if (selectedItems == null || selectedItems.isEmpty()) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, Messages.getMessage("error.addtocart.notselected"), "error.addtocart.notselected");
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

		productList = bookLogic.getAllBookISBNs();
		productListView = bookLogic.createVBookList(productList, newCart);

		if (DEBUG_DATA_DETAIL)
			productListView.stream().forEach(book -> {
				System.out.println(String.format("isbn=%s, title=%s, selected=%s", book.getIsbn(), book.getTitle(), book.isSelected()));
			});

		return "BookStore";
	}

	public String clearCart() {
		System.out.println("BookStoreBean.clearCart: this=" + this);

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(false);
		if (session == null) {
			return "sessionError.html";
		}

		//productList = bookLogic.getAllBookISBNs()
		//productListView = bookLogic.createVBookList(productList, null)
		productList = null;
		productListView = null;

		session.removeAttribute("Cart");
		selectedItems = null;

		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
				Messages.getMessage("info.cart.clear"), "[info.cart.clear]です。");
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, fm);
		return "BookStore";
	}

	public void selectValueChange(ValueChangeEvent e) {
		System.out.println("BookStoreBean.selectValueChange: this=" + this);

		HtmlSelectBooleanCheckbox cb = (HtmlSelectBooleanCheckbox)e.getSource();
		String label = cb.getLabel();
		if (selectedItems == null) {
			if (DEBUG_EVENT)
				System.out.println("BookStoreBean: created selectedItems");
			selectedItems = new ArrayList<>();
		}

		if ("true".equals(e.getNewValue().toString())) {
			if (selectedItems.contains(label)) {
				if (DEBUG_EVENT)
					System.out.println("BookStoreBean.selectValueChange: item found => skip. cb.label=" + cb.getLabel());
			}
			else {
				if (DEBUG_EVENT)
					System.out.println("BookStoreBean.selectValueChange: item not found => add. cb.label=" + cb.getLabel());
				selectedItems.add(label);
			}
		}
		else {
			if (selectedItems.contains(label)) {
				if (DEBUG_EVENT)
					System.out.println("BookStoreBean.selectValueChange: item found => remove. cb.label=" + cb.getLabel());
				selectedItems.remove(label);
			}
			else {
				if (DEBUG_EVENT)
					System.out.println("BookStoreBean.selectValueChange: item not found => skip. cb.label=" + cb.getLabel());
			}
		}
	}

}
