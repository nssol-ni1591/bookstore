package bookstore.jsf.bean.eclipselink;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import bookstore.annotation.UsedWeld;
import bookstore.jsf.bean.AbstractBookStoreBean;
import bookstore.logic.BookLogic;

@Named
@SessionScoped
public class BookStoreBean extends AbstractBookStoreBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject @UsedWeld private BookLogic bookLogic;

	public BookStoreBean() {
		super();
	}

	protected String getBookStorePage() {
		return "BookStore";
	}
	protected BookLogic getBookLogic() {
		return bookLogic;
	}
	
}
