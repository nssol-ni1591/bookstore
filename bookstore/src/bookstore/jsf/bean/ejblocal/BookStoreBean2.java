package bookstore.jsf.bean.ejblocal;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import bookstore.jsf.bean.AbstractBookStoreBean;
import bookstore.service.BookService;
import bookstore.service.ejb.cmt.BookServiceWrapper;

@Named
@SessionScoped
public class BookStoreBean2 extends AbstractBookStoreBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB private transient BookServiceWrapper bookService;

	public BookStoreBean2() {
		super();
	}

	protected String getBookStorePage() {
		return "BookStore2";
	}
	protected BookService getBookService() {
		return bookService;
	}

}
