package bookstore.jsf.bean.ejbcmt;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import bookstore.jsf.bean.AbstractBookStoreBean;
import bookstore.service.BookService;
import bookstore.service.ejb.BookServiceLocal;

@Named
@SessionScoped
public class BookStoreBean3 extends AbstractBookStoreBean implements Serializable {

	private static final long serialVersionUID = 1L;

	//@Local
	@EJB(mappedName="BookServiceCmtWrapper") private transient BookServiceLocal bookService;


	@Override
	protected String getBookStorePage() {
		return "BookStore3";
	}
	@Override
	protected BookService getBookService() {
		return bookService;
	}

}
