package bookstore.jsf.bean;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import bookstore.logic.BookLogic;

@Named
@SessionScoped
public class BookStoreBean3 extends AbstractBookStoreBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB(mappedName="BookLogicCmtWrapper") private BookLogic bookLogic;

	public BookStoreBean3() {
		super();
	}

	protected String getBookStorePage() {
		return "BookStore3";
	}
	protected BookLogic getBookLogic() {
		return bookLogic;
	}

}
