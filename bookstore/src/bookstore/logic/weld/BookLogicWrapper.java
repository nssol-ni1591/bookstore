package bookstore.logic.weld;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import bookstore.annotation.UsedWeld;
import bookstore.annotation.UsedEclipselink;
import bookstore.dao.BookDAO;
import bookstore.logic.impl.BookLogicImpl;

@UsedWeld
public class BookLogicWrapper extends BookLogicImpl implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject @UsedEclipselink private BookDAO bookdao;

	@PostConstruct
	public void init() {
		super.setBookdao(bookdao);
	}

}
