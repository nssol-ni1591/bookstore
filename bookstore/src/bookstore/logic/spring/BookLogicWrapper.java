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
public class BookLogicWrapper extends AbstractBookLogic {

	@Autowired @Qualifier("BookDAOBId") BookDAO bookdao;
	@Log private static Logger log;

	@Override
	protected BookDAO getBookDAO() {
		return bookdao;
	}
	@Override
	protected Logger getLogger() {
		return log;
	}

	public void setBookdao(BookDAO bookdao) {
		this.bookdao = bookdao;
	}

}
