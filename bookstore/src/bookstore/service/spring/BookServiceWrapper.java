package bookstore.service.spring;

import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import bookstore.annotation.Log;
import bookstore.annotation.UsedSpring;
import bookstore.dao.BookDAO;
import bookstore.service.AbstractBookService;

@UsedSpring
@Component("ServiceBookImplBId")
public class BookServiceWrapper extends AbstractBookService<SessionFactory> {

	@Log private static Logger log;

	private BookDAO<SessionFactory> bookdao;
	//private SessionFactory sessionFactory

	@Override
	protected BookDAO<SessionFactory> getBookDAO() {
		return bookdao;
	}
	@Override
	protected Logger getLogger() {
		return log;
	}
	@Override
	protected SessionFactory getManager() {
		// ここでnullを指定してDAO実装クラス側でDIしたsessionFactoryを使用しても
		// Tx動作に違いはない。らしい
		//return sessionFactory
		return null;
	}

	// DIを実現するためのSetterメソッドs
	public void setBookdao(BookDAO<SessionFactory> bookdao) {
		this.bookdao = bookdao;
	}

}
