package bookstore.dao.eclipselink;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import bookstore.dao.BookDAO;
import bookstore.pbean.TBook;

public class BookDAOImpl implements BookDAO {

	@PersistenceContext(unitName = "BookStore")
	//private EntityManager em;
	private EntityManager em = Persistence.createEntityManagerFactory("BookStore").createEntityManager();

	@Override
	public int getPriceByISBNs(List<String> inISBNList) {
		Query q = em
				.createQuery("select sum( book.price ) from TBook book where book.isbn in :SELECTED_ITEMS");
		q.setParameter("SELECTED_ITEMS", inISBNList);
		Object o = q.getSingleResult();
		System.out.println("eclipselink.BookDAOImpl.getPriceByISBNs: sum=" + o);
		return ((Long) q.getSingleResult()).intValue();
	}

	@Override
	public List<TBook> retrieveBooksByKeyword(String inKeyword) {
		Query q = em
				.createQuery("select b from TBook b where "
						+ "b.author like :keyword or b.title like :keyword or b.publisher like :keyword");
		q.setParameter("keyword", "%" + inKeyword + "%");

		@SuppressWarnings("unchecked")
		List<TBook> list = q.getResultList();
		System.out.println("eclipselink.BookDAOImpl.retrieveBooksByKeyword: keyword="
				+ inKeyword + ", size=" + list.size());
		return list;
	}

	@Override
	public List<TBook> retrieveBooksByISBNs(List<String> inISBNList) {
		System.out.println("eclipselink.BookDAOImpl.retrieveBooksByISBNs: inISBNList="
				+ inISBNList);

		Query q;
		if (inISBNList == null) {
			q = em.createQuery("select b from TBook b");
			@SuppressWarnings("unchecked")
			List<TBook> resultList = q.getResultList();
			return resultList;
		}

		/*
		 * StringBuffer sb = new StringBuffer(); ArrayList list = new
		 * ArrayList<String>(); Iterator<String> it = inISBNList.iterator(); if
		 * (it.hasNext()) { String isbn = it.next(); sb.append("'");
		 * sb.append(isbn); sb.append("'"); list.add("'" + isbn + "'"); } while
		 * (it.hasNext()) { String isbn = it.next(); sb.append(",'");
		 * sb.append(isbn); sb.append("'"); list.add("'" + isbn + "'"); }
		 * System.out.println("BookDAOImpl.retrieveBooksByISBNs: String=" +
		 * sb.toString());
		 * System.out.println("BookDAOImpl.retrieveBooksByISBNs: list=" +
		 * list.toString());
		 */
		q = em.createQuery("select b from TBook b where b.isbn in :inISBNList");
		// q =
		// em.createQuery("select b from TBook b where b.isbn in ( :inISBNList )");
		// q = em.createQuery("select b from TBook b where b.isbn in (" +
		// sb.toString() + ")");

		q.setParameter("inISBNList", inISBNList);
		// q.setParameter("inISBNList", inISBNList.toString());
		// q.setParameter("inISBNList", sb.toString());
		// q.setParameter("inISBNList", list);

		@SuppressWarnings("unchecked")
		List<TBook> resultList = q.getResultList();
		return resultList;
	}

}
