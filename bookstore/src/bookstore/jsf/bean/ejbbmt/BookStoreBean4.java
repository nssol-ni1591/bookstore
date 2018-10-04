package bookstore.jsf.bean.ejbbmt;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import bookstore.jsf.bean.AbstractBookStoreBean;
import bookstore.service.BookService;

@Named
@SessionScoped
public class BookStoreBean4 extends AbstractBookStoreBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB(mappedName="BookServiceBmtWrapper") private BookService bookService;

	public BookStoreBean4() {
		super();
	}

	protected String getBookStorePage() {
		return "BookStore4";
	}
	protected BookService getBookService() {
		return bookService;
	}

}
