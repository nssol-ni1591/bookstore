package bookstore.logic;

import bookstore.dao.impl.BookDAOImpl;

public class BookLogicImpl2 extends BookLogicImpl {

	public BookLogicImpl2() {
		setBookdao(new BookDAOImpl());
	}

}
