package bookstore.dao.jpa;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import bookstore.dao.BookDAO;
import bookstore.pbean.TBook;

public abstract class AbstractBookDAOImpl<T extends EntityManager> implements BookDAO<T> {

	/*
	 * transaction-typeÇ™JTAÇ©RESOURCE_LOCALÇ∆ÇÃà·Ç¢ÇÕÅA
	 * EntityManagerÇÃéÊìæï˚ñ@ÇÃà·Ç¢ÇæÇØ
	 */
	protected abstract EntityManager getEntityManager();
	protected abstract Logger getLogger();


	@Override
	public int getPriceByISBNs(final T em2, List<String> isbnList) {
		EntityManager em = em2 != null ? em2 : getEntityManager();

		Query q = em
				.createQuery("select sum( book.price ) from TBook book where book.isbn in :SELECTED_ITEMS");
		q.setParameter("SELECTED_ITEMS", isbnList);
		return ((Long) q.getSingleResult()).intValue();
	}

	@Override
	public List<TBook> retrieveBooksByKeyword(final T em2, String keyword) {
		EntityManager em = em2 != null ? em2 : getEntityManager();

		Query q = em
				.createQuery("select b from TBook b where "
						+ "b.author like :keyword or b.title like :keyword or b.publisher like :keyword");
		q.setParameter("keyword", "%" + keyword + "%");
		@SuppressWarnings("unchecked")
		List<TBook> list = q.getResultList();
		return list;
	}

	@Override
	public List<TBook> retrieveBooksByISBNs(final T em2, List<String> isbnList) {
		EntityManager em = em2 != null ? em2 : getEntityManager();

		Query q;
		if (isbnList == null) {
			q = em.createQuery("select b from TBook b");
			@SuppressWarnings("unchecked")
			List<TBook> resultList = q.getResultList();
			return resultList;
		}
		q = em.createQuery("select b from TBook b where b.isbn in :isbnList");
		q.setParameter("isbnList", isbnList);
		@SuppressWarnings("unchecked")
		List<TBook> resultList = q.getResultList();
		return resultList;
	}

}
