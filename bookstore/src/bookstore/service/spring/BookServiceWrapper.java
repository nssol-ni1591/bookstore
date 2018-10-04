package bookstore.service.spring;

import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import bookstore.annotation.Log;
import bookstore.annotation.UsedSpring;
import bookstore.dao.BookDAO;
import bookstore.service.AbstractBookService;

@UsedSpring
@Component("ServiceBookImplBId")
public class BookServiceWrapper extends AbstractBookService<SessionFactory> {

	@Log private static Logger log;

	@Autowired @Qualifier("BookDAOBId") BookDAO<SessionFactory> bookdao;
	@Autowired @Qualifier("sessionFactory") SessionFactory sessionFactory;

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
		return sessionFactory;
	}

}
