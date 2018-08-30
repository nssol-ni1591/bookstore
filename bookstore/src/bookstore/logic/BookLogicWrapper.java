package bookstore.logic;

import bookstore.dao.impl.BookDAOImpl;

public class BookLogicWrapper extends BookLogicImpl {

	public BookLogicWrapper() {
		setBookdao(new BookDAOImpl());
	}

}
