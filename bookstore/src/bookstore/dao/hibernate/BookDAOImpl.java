package bookstore.dao.hibernate;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import bookstore.annotation.Log;
import bookstore.dao.BookDAO;
import bookstore.pbean.TBook;

@Repository("BookDAOImplBId")
public class BookDAOImpl<T extends SessionFactory> /*extends HibernateDaoSupport*/ implements BookDAO<T> {

	// @Autowiredでもコンテキストxmlでも、少なくとも一連の処理では同じインスタンスが設定される
	private SessionFactory sessionFactory3;
	//@Autowired SessionFactory sessionFactory3
	@Log private static Logger log;

	@Override
	public int getPriceByISBNs(final T sessionFactory2, final List<String> inISBNList) {
		SessionFactory sessionFactory = sessionFactory2 != null ? sessionFactory2 : sessionFactory3;

		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		return ht.execute(session -> {
			Query priceQuery = 
					session.createQuery("select sum( book.price ) from TBook book where book.isbn in ( :SELECTED_ITEMS )");
			priceQuery.setParameterList("SELECTED_ITEMS", inISBNList);
			return (Long)priceQuery.uniqueResult();
		}).intValue();
	}

	@Override
	public List<TBook> retrieveBooksByKeyword(final T sessionFactory2, String inKeyword) {
		SessionFactory sessionFactory = sessionFactory2 != null ? sessionFactory2 : sessionFactory3;

		String escapedKeyword = Pattern.compile("([%_])").matcher(inKeyword).replaceAll("\\\\$1");
		Object[] keywords = { "%" + escapedKeyword + "%", "%" + escapedKeyword + "%", "%" + escapedKeyword + "%" };

		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		@SuppressWarnings("unchecked")
		List<TBook> booksList = (List<TBook>) ht
				.find("from TBook book where book.author like ?" + "or book.title like ? or book.publisher like ?",
						keywords);
		return booksList;
	}

	@Override
	public List<TBook> retrieveBooksByISBNs(final T sessionFactory2, final List<String> inISBNList) {
		SessionFactory sessionFactory = sessionFactory2 != null ? sessionFactory2 : sessionFactory3;

		HibernateTemplate ht = new HibernateTemplate(sessionFactory);
		List<TBook> list = null;
		if (inISBNList == null) {
			@SuppressWarnings("unchecked")
			List<TBook> list2 = (List<TBook>) ht.find("from TBook book");
			list = list2;	// list2の右辺を直接listに代入するとエラーなる WHY?
		}
		else {
			list = ht.execute(session -> {
				Query retrieveQuery = session.createQuery("from TBook book where book.isbn in ( :ISBNS )");
				retrieveQuery.setParameterList("ISBNS", inISBNList);

				log.log(Level.INFO, "inISBNList={0}", retrieveQuery);
				log.log(Level.INFO, "retrieveQuery={0}", retrieveQuery);

				@SuppressWarnings("unchecked")
				List<TBook> list2 = (List<TBook>)retrieveQuery.list();
				return list2;
			});
		}
		return list;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory3 = sessionFactory;
	}

}
