package bookstore.dao.hibernate;

import java.util.List;
import java.util.regex.Pattern;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import bookstore.dao.BookDAO;
import bookstore.pbean.TBook;

public class BookDAOImpl extends HibernateDaoSupport implements BookDAO {

	@Override
	public int getPriceByISBNs(final List<String> inISBNList) {
		HibernateTemplate ht = getHibernateTemplate();

		return ht.execute(new HibernateCallback<Long>() {

			public Long doInHibernate(Session session) throws HibernateException {
				Query priceQuery = 
						session.createQuery("select sum( book.price ) from TBook book where book.isbn in ( :SELECTED_ITEMS )");
				priceQuery.setParameterList("SELECTED_ITEMS", inISBNList);
				return (Long)priceQuery.uniqueResult();
			}}).intValue();
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

			return ht.execute(new HibernateCallback<List<TBook>>() {

				public List<TBook> doInHibernate(Session session) throws HibernateException {

					Query retrieveQuery = session.createQuery("from TBook book where book.isbn in ( :ISBNS )");
					retrieveQuery.setParameterList("ISBNS", inISBNList);

					System.out.println("inISBNList=\"" + inISBNList + "\"");
					System.out.println("retrieveQuery=\"" + retrieveQuery + "\"");

					return retrieveQuery.list();
				}
			});
		}
	}
}
