package bookstore.logic.wrapper;

import bookstore.dao.impl.BookDAOImpl;
import bookstore.logic.BookLogicImpl;

public class BookLogicWrapper extends BookLogicImpl {

	public BookLogicWrapper() {
		setBookdao(new BookDAOImpl());
	}

}
