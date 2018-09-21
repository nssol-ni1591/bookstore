package bookstore.logic.pojo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import bookstore.annotation.UsedPojo;
import bookstore.dao.BookDAO;
import bookstore.dao.jdbc.BookDAOImpl;
import bookstore.dao.jdbc.DB;
import bookstore.logic.AbstractBookLogic;

@UsedPojo
public class BookLogicWrapper extends AbstractBookLogic<Connection> {

	private final BookDAO<Connection> bookdao = new BookDAOImpl<>();
	private static final Logger log = Logger.getLogger(BookLogicWrapper.class.getName());

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
		try {
			return DB.createConnection();
		}
		catch (SQLException e) {
			log.log(Level.SEVERE, "", e);
		}
		return null;
	}

}
