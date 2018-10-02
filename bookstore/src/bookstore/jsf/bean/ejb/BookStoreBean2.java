package bookstore.jsf.bean.ejb;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import bookstore.jsf.bean.AbstractBookStoreBean;
import bookstore.logic.BookLogic;
import bookstore.logic.ejb.cmt.BookLogicWrapper;

@Named
@SessionScoped
public class BookStoreBean2 extends AbstractBookStoreBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB private BookLogicWrapper bookLogic;

	public BookStoreBean2() {
		super();
	}

	protected String getBookStorePage() {
		return "BookStore2";
	}
	protected BookLogic getBookLogic() {
		return bookLogic;
	}

}
