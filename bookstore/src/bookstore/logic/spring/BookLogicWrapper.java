package bookstore.logic.spring;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import bookstore.annotation.Log;
import bookstore.annotation.UsedSpring;
import bookstore.dao.BookDAO;
import bookstore.logic.AbstractBookLogic;

@UsedSpring
@Component("LogicBookImplBId")
public class BookLogicWrapper extends AbstractBookLogic<Object> {

	@Autowired @Qualifier("BookDAOBId") BookDAO<Object> bookdao;
	@Log private static Logger log;

	@Override
	protected BookDAO<Object> getBookDAO() {
		return bookdao;
	}
	@Override
	protected Logger getLogger() {
		return log;
	}
	@Override
	protected Object getManager() {
		return null;
	}

	public void setBookdao(BookDAO<Object> bookdao) {
		this.bookdao = bookdao;
	}

}
