package bookstore.jsf.bean.ejbcmt;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import bookstore.jsf.bean.AbstractBookStoreBean;
import bookstore.service.BookService;

@Named
@SessionScoped
public class BookStoreBean3 extends AbstractBookStoreBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB(mappedName="BookServiceCmtWrapper") private transient BookService bookService;

	public BookStoreBean3() {
		super();
	}

	protected String getBookStorePage() {
		return "BookStore3";
	}
	protected BookService getBookService() {
		return bookService;
	}

}
