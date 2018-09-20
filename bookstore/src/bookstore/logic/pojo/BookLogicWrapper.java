package bookstore.logic.pojo;

import java.util.logging.Logger;

import bookstore.annotation.UsedPojo;
import bookstore.dao.BookDAO;
import bookstore.dao.jdbc.BookDAOImpl;
import bookstore.logic.AbstractBookLogic;

@UsedPojo
public class BookLogicWrapper extends AbstractBookLogic {

	private final BookDAO bookdao = new BookDAOImpl();
	private static final Logger log = Logger.getLogger(BookLogicWrapper.class.getName());

	@Override
	protected BookDAO getBookDAO() {
		return bookdao;
	}
	@Override
	protected Logger getLogger() {
		return log;
	}

}
