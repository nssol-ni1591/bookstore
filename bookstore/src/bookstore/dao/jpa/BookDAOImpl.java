package bookstore.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import bookstore.dao.BookDAO;
import bookstore.pbean.TBook;

public abstract class BookDAOImpl<T extends EntityManager> implements BookDAO<T> {
	/*
	 * RESOURCE_LOCALとJTA永続コンテキストの比較
	 * <persistence-unit transaction-type = "RESOURCE_LOCAL">を使用すると、
	 * EntityManager（PersistenceContext / Cache）の作成と追跡が行われます。
	 * 
	 * EntityManagerFactoryを使用してEntityManagerを取得する必要があります
	 * 生成されるEntityManagerインスタンスは 、PersistenceContext / Cacheです。
	 * @PersistenceUnit注釈だけでEntityManagerFactoryを挿入できます（@PersistenceContextではありません）
	 * 
	 * @PersistenceContextを使用してRESOURCE_LOCALタイプのユニットを参照することはできません
	 * EntityMangerへのすべての呼び出しの前後でEntityTransaction APIを使用して開始/コミットする必要があります
	 * 
	 * entityManagerFactory.createEntityManager（）を2回呼び出すと、 2つの別々のEntityManager
	 * インスタンスが生成され、そのために2つの別々のPersistenceContext / Cacheが生成されます。
	 * 
	 * EntityManagerの複数のインスタンスを使用することをお勧めします
	 * （注意：最初のインスタンスを破棄しない限り、2つ目のインスタンスを作成しないでください）
	 */

	protected abstract EntityManager getEntityManager();

	@Override
	public int getPriceByISBNs(final T em2, List<String> inISBNList) {
		EntityManager em =  getEntityManager();
		Query q = em
				.createQuery("select sum( book.price ) from TBook book where book.isbn in :SELECTED_ITEMS");
		q.setParameter("SELECTED_ITEMS", inISBNList);
		return ((Long) q.getSingleResult()).intValue();
	}

	@Override
	public List<TBook> retrieveBooksByKeyword(final T em2, String inKeyword) {
		EntityManager em =  getEntityManager();
		Query q = em
				.createQuery("select b from TBook b where "
						+ "b.author like :keyword or b.title like :keyword or b.publisher like :keyword");
		q.setParameter("keyword", "%" + inKeyword + "%");

		@SuppressWarnings("unchecked")
		List<TBook> list = q.getResultList();
		return list;
	}

	@Override
	public List<TBook> retrieveBooksByISBNs(final T em2, List<String> inISBNList) {
		EntityManager em =  getEntityManager();
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
