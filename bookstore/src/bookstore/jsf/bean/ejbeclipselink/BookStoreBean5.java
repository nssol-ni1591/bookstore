package bookstore.jsf.bean.ejbeclipselink;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import bookstore.jsf.bean.AbstractBookStoreBean;
import bookstore.logic.BookLogic;

@Named
@SessionScoped
public class BookStoreBean5 extends AbstractBookStoreBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB(mappedName="BookLogicEclipseLinkWrapper") private BookLogic bookLogic;

	public BookStoreBean5() {
		super();
	}

	protected String getBookStorePage() {
		return "BookStore5";
	}
	protected BookLogic getBookLogic() {
		return bookLogic;
	}

}
