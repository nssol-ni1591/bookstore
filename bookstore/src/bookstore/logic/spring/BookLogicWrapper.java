package bookstore.logic.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import bookstore.annotation.UsedSpring;
import bookstore.dao.BookDAO;
import bookstore.logic.impl.BookLogicImpl;

@UsedSpring
@Component("LogicBookImplBId")
public class BookLogicWrapper extends BookLogicImpl {

	@Autowired @Qualifier("BookDAOBId") BookDAO bookdao;

	@Override
	public void setBookdao(BookDAO bookdao) {
		this.bookdao = bookdao;
		super.setBookdao(bookdao);
	}

}
