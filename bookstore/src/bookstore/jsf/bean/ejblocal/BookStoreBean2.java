package bookstore.jsf.bean.ejblocal;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import bookstore.jsf.bean.AbstractBookStoreBean;
import bookstore.service.BookService;

@Named
@SessionScoped
public class BookStoreBean2 extends AbstractBookStoreBean implements Serializable {

	// for @SessionScope
	private static final long serialVersionUID = 1L;

	//@LocalBean
	@EJB private transient BookService bookService;


	@Override
	protected String getBookStorePage() {
		return "BookStore2";
	}
	@Override
	protected BookService getBookService() {
		return bookService;
	}

}
