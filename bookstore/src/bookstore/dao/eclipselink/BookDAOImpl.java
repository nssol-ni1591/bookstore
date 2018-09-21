package bookstore.dao.eclipselink;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import bookstore.annotation.UsedEclipselink;
import bookstore.dao.BookDAO;
import bookstore.pbean.TBook;

@UsedEclipselink
@Dependent
public class BookDAOImpl<T extends EntityManager> implements BookDAO<T> {

	//Tomcat‚Å‚Í@PersistenceContext‚ÍŽg‚¦‚È‚¢
	//@PersistenceContext(unitName = "BookStore") private EntityManager em;
	@PersistenceUnit(name = "BookStore") private EntityManagerFactory emf;
	//private EntityManager em = Persistence.createEntityManagerFactory("BookStore").createEntityManager()
	//@Inject private EntityManager em;
	@Inject private Logger log;

	@Override
	public int getPriceByISBNs(final T em2, List<String> inISBNList) {
		EntityManager em = em2 != null ? em2 : emf.createEntityManager();
		Query q = em
				.createQuery("select sum( book.price ) from TBook book where book.isbn in :SELECTED_ITEMS");
		q.setParameter("SELECTED_ITEMS", inISBNList);
		Object o = q.getSingleResult();
		log.log(Level.FINE, "getPriceByISBNs: sum={0}", o);
		return ((Long) q.getSingleResult()).intValue();
	}

	@Override
	public List<TBook> retrieveBooksByKeyword(final T em2, String inKeyword) {
		EntityManager em = em2 != null ? em2 : emf.createEntityManager();
		Query q = em
				.createQuery("select b from TBook b where "
						+ "b.author like :keyword or b.title like :keyword or b.publisher like :keyword");
		q.setParameter("keyword", "%" + inKeyword + "%");

		@SuppressWarnings("unchecked")
		List<TBook> list = q.getResultList();
		log.log(Level.FINE, "keyword={0}, size={1}"
				, new Object[] { inKeyword, list.size() });
		return list;
	}

	@Override
	public List<TBook> retrieveBooksByISBNs(final T em2, List<String> inISBNList) {
		log.log(Level.FINE, "inISBNList={0}", inISBNList);
		EntityManager em = em2 != null ? em2 : emf.createEntityManager();
		Query q;
		if (inISBNList == null) {
			q = em.createQuery("select b from TBook b");
			@SuppressWarnings("unchecked")
			List<TBook> resultList = q.getResultList();
			return resultList;
		}

		q = em.createQuery("select b from TBook b where b.isbn in :inISBNList");
		q.setParameter("inISBNList", inISBNList);
		@SuppressWarnings("unchecked")
		List<TBook> resultList = q.getResultList();
		return resultList;
	}

}
