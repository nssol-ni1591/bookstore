package bookstore.dao.hibernate;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import bookstore.annotation.Log;
import bookstore.dao.BookDAO;
import bookstore.pbean.TBook;

@Repository("BookDAOImplBId")
public class BookDAOImpl extends HibernateDaoSupport implements BookDAO {

	@Log private static Logger log;

	@Autowired @Qualifier("sessionFactory") SessionFactory sessionFactory;

	@Override
	public int getPriceByISBNs(final List<String> inISBNList) {
		HibernateTemplate ht = getHibernateTemplate();
		return ht.execute(session -> {
			Query priceQuery = 
					session.createQuery("select sum( book.price ) from TBook book where book.isbn in ( :SELECTED_ITEMS )");
			priceQuery.setParameterList("SELECTED_ITEMS", inISBNList);
			return (Long)priceQuery.uniqueResult();
		}).intValue();
	}

	@Override
	public List<TBook> retrieveBooksByKeyword(String inKeyword) {
		String escapedKeyword = Pattern.compile("([%_])").matcher(inKeyword).replaceAll("\\\\$1");
		Object[] keywords = { "%" + escapedKeyword + "%", "%" + escapedKeyword + "%", "%" + escapedKeyword + "%" };

		@SuppressWarnings("unchecked")
		List<TBook> booksList = (List<TBook>)getHibernateTemplate()
				.find("from TBook book where book.author like ?" + "or book.title like ? or book.publisher like ?",
						keywords);
		return booksList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TBook> retrieveBooksByISBNs(final List<String> inISBNList) {

		HibernateTemplate ht = getHibernateTemplate();

		if (inISBNList == null) {
			return ht.find("from TBook book");
		}
		else {
			return ht.execute(session -> {
				Query retrieveQuery = session.createQuery("from TBook book where book.isbn in ( :ISBNS )");
				retrieveQuery.setParameterList("ISBNS", inISBNList);

				log.log(Level.INFO, "inISBNList={0}", retrieveQuery);
				log.log(Level.INFO, "retrieveQuery={0}", retrieveQuery);
				return retrieveQuery.list();
				
			});
		}
	}
}
