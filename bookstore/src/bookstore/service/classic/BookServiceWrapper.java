package bookstore.service.classic;

import java.sql.Connection;
import java.util.logging.Logger;

import bookstore.annotation.UsedClassic;
import bookstore.dao.BookDAO;
import bookstore.dao.jdbc.BookDAOImpl;
import bookstore.service.AbstractBookService;

@UsedClassic
public class BookServiceWrapper extends AbstractBookService<Connection> {

	private final BookDAO<Connection> bookdao = new BookDAOImpl<>();
	private static final Logger log = Logger.getLogger(BookServiceWrapper.class.getName());

	private Connection con = null;

	@Override
	protected BookDAO<Connection> getBookDAO() {
		return bookdao;
	}
	@Override
	protected Logger getLogger() {
		return log;
	}
	@Override
	protected Connection getManager() {
		return con;
	}

}