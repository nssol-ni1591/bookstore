package bookstore.jsf.bean.ejbbmt;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import bookstore.jsf.bean.AbstractBookStoreBean;
import bookstore.service.BookService;
import bookstore.service.ejb.BookServiceRemote;

@Named
@SessionScoped
public class BookStoreBean4 extends AbstractBookStoreBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// @Remote
	@EJB(mappedName="BookServiceBmtWrapper") private transient BookServiceRemote bookService;


	@Override
	protected String getBookStorePage() {
		return "BookStore4";
	}
	@Override
	protected BookService getBookService() {
		return bookService;
	}

}
