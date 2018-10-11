package bookstore.jsf.bean.ejb;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import bookstore.jsf.bean.AbstractBookStoreBean;
import bookstore.service.BookService;

@Named
@SessionScoped
public class BookStoreBean6 extends AbstractBookStoreBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB(mappedName="BookServiceEjbBmtWrapper") private transient BookService bookService;

	public BookStoreBean6() {
		super();
	}

	protected String getBookStorePage() {
		return "BookStore6";
	}
	protected BookService getBookService() {
		return bookService;
	}

}