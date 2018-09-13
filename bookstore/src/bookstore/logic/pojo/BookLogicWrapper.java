package bookstore.logic.pojo;

import bookstore.annotation.UsedPojo;
import bookstore.dao.jdbc.BookDAOImpl;
import bookstore.logic.impl.BookLogicImpl;

@UsedPojo
public class BookLogicWrapper extends BookLogicImpl {

	public BookLogicWrapper() {
		setBookdao(new BookDAOImpl());
	}

}
