package bookstore.jsf.bean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import bookstore.service.BookService;
import bookstore.util.Messages;
import bookstore.vbean.VBook;

public abstract class AbstractBookStoreBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String SESSION_ERROR = "sessionError.html";
	private static final String THIS_0 = "this={0}";

	@Inject private transient Logger log;

	private String keyword;

	private List<String> productList;
	private List<VBook> productListView;
	private List<String> selectedItems;

	protected abstract String getBookStorePage();
	protected abstract BookService getBookLogic();

	public AbstractBookStoreBean() {
		//log.log(Level.INFO, "this={0}", this)
		//このタイミングでは早すぎる
	}

	@PostConstruct
	public void init() {
		log.log(Level.INFO, THIS_0, this);
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

	public List<VBook> getBookList() throws SQLException {
		if (productListView != null) {
			log.log(Level.FINE, "(1): bookList.size={0}, bookList={1}"
					, new Object[] { productListView.size(), productListView });
			return productListView;
		}
		BookService bookLogic = getBookLogic();

		// LoginBeanから遷移してきた場合はproductListViewがnullになる
		if (productList == null || productList.isEmpty()) {
			productList = bookLogic.getAllBookISBNs();
		}
		productListView = bookLogic.createVBookList(productList, null);
		log.log(Level.FINE, "(2): bookList.size={0}, bookList={1}"
				, new Object[] { productListView.size(), productListView });
		return productListView;
	}

	public String search() throws SQLException {
		log.log(Level.INFO, THIS_0, this);

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(false);
		if (session == null) {
			return SESSION_ERROR;
		}

		log.log(Level.FINE, "keyword={0}", keyword);

		BookService bookLogic = getBookLogic();
		List<String> foundBooks = bookLogic.retrieveBookISBNsByKeyword(keyword);
		if (foundBooks == null || foundBooks.isEmpty()) {
			foundBooks = bookLogic.getAllBookISBNs();
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR
					, Messages.getMessage("error.search.notfound")
					, "[error.search.notfound]です。");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
		}
		@SuppressWarnings("unchecked")
		List<String> cart = (List<String>) session.getAttribute("Cart");
		log.log(Level.INFO, "cart.size={0}, cart={1}"
				, new Object[] { cart == null ? 0 : cart.size(), cart });

		productListView = bookLogic.createVBookList(foundBooks, cart);
		log.log(Level.INFO, "productListView.size={0}, productListView={1}"
				, new Object[] { productListView == null ? 0 : productListView.size(), productListView });

		// 検索結果から外れたselected状態にあったItemが、
		// 次のaddToCartでカートに追加されるのを防ぐため、
		// addToCartの対象を絞り込む
		productList = foundBooks;

		// @SessionScopeにしたので初期化する必要があり
		keyword = "";
		return getBookStorePage();
	}

	public String addToCart() throws SQLException {
		log.log(Level.FINE, THIS_0, this);

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(false);
		if (session == null) {
			return SESSION_ERROR;
		}

		@SuppressWarnings("unchecked")
		List<String> cart = (List<String>)session.getAttribute("Cart");
		if (cart == null) {
			cart = new ArrayList<>();
		}
		log.log(Level.INFO, "cart.size={0}, cart={1}", new Object[] { cart.size(), cart });

		log.log(Level.INFO, "productList.size={0}, productList={1}"
				, new Object[] { productList.size(), productList });

		log.log(Level.INFO, "selectedItems.size={0}, selectedItems={1}"
				, new Object[] { selectedItems == null ? 0 : selectedItems.size(), selectedItems });
		if (selectedItems == null || selectedItems.isEmpty()) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR
					, Messages.getMessage("error.addtocart.notselected")
					, "error.addtocart.notselected");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
			return getBookStorePage();
		}

		BookService bookLogic = getBookLogic();
		List<String> newCart = bookLogic.createCart(productList, selectedItems, cart);
		session.setAttribute("Cart", newCart);
		log.log(Level.INFO, "newCart.size={0}, newCart={1}", new Object[] { newCart.size(),  newCart });

		FacesContext fc = FacesContext.getCurrentInstance();
		for (String book : newCart) {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO
					, "info.cart.books = " + book
					, "[info.cart.books] = " + book + "です。");
			fc.addMessage(null, fm);
		}

		productList = bookLogic.getAllBookISBNs();
		productListView = bookLogic.createVBookList(productList, newCart);

		productListView.stream().forEach(book ->
			log.log(Level.FINEST, "isbn={0}, title={1}, selected={2}"
					, new Object[] { book.getIsbn(), book.getTitle(), book.isSelected() })
				);

		return getBookStorePage();
	}

	public String clearCart() {
		log.log(Level.FINE, THIS_0, this);

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(false);
		if (session == null) {
			return SESSION_ERROR;
		}

		//productList = bookLogic.getAllBookISBNs()
		//productListView = bookLogic.createVBookList(productList, null)
		productList = null;
		productListView = null;

		session.removeAttribute("Cart");
		selectedItems = null;

		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO
				, Messages.getMessage("info.cart.clear")
				, "[info.cart.clear]です。");
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, fm);
		return getBookStorePage();
	}

	public void selectValueChange(ValueChangeEvent e) {
		log.log(Level.FINEST, THIS_0, this);

		HtmlSelectBooleanCheckbox cb = (HtmlSelectBooleanCheckbox)e.getSource();
		String label = cb.getLabel();
		if (selectedItems == null) {
			log.log(Level.FINEST, "created selectedItems");
			selectedItems = new ArrayList<>();
		}

		if ("true".equals(e.getNewValue().toString())) {
			if (selectedItems.contains(label)) {
				log.log(Level.FINEST, "item found => skip. cb.label={0}", cb.getLabel());
			}
			else {
				log.log(Level.FINEST, "item not found => add. cb.label={0}", cb.getLabel());
				selectedItems.add(label);
			}
		}
		else {
			if (selectedItems.contains(label)) {
				log.log(Level.FINE, "item found => remove. cb.label={0}", cb.getLabel());
				selectedItems.remove(label);
			}
			else {
				log.log(Level.FINE, "item not found => skip. cb.label={0}", cb.getLabel());
			}
		}
	}

}
