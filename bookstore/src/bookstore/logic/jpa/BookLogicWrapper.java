package bookstore.logic.jpa;

import bookstore.dao.eclipselink.BookDAOImpl;
import bookstore.logic.BookLogicImpl;

public class BookLogicWrapper extends BookLogicImpl {

	public BookLogicWrapper() {
		setBookdao(new BookDAOImpl());
	}

}
