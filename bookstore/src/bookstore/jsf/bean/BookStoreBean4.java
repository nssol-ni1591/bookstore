package bookstore.jsf.bean;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import bookstore.logic.BookLogic;

@Named
@SessionScoped
public class BookStoreBean4 extends AbstractBookStoreBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB(mappedName="BookLogicBmtWrapper") private BookLogic bookLogic;

	public BookStoreBean4() {
		super();
	}

	protected String getBookStorePage() {
		return "BookStore4";
	}
	protected BookLogic getBookLogic() {
		return bookLogic;
	}

}
