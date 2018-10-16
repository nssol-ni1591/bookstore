package bookstore.dao.jpa;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import bookstore.dao.BookDAO;
import bookstore.pbean.TBook;

public abstract class AbstractBookDAOImpl<T extends EntityManager> implements BookDAO<T> {
	/*
	 * RESOURCE_LOCAL��JTA�i���R���e�L�X�g�̔�r
	 * <persistence-unit transaction-type = "RESOURCE_LOCAL">���g�p����ƁA
	 * EntityManager�iPersistenceContext / Cache�j�̍쐬�ƒǐՂ��s���܂��B
	 * 
	 * EntityManagerFactory���g�p����EntityManager���擾����K�v������܂�
	 * ���������EntityManager�C���X�^���X�� �APersistenceContext / Cache�ł��B
	 * @PersistenceUnit���߂�����EntityManagerFactory��}���ł��܂��i@PersistenceContext�ł͂���܂���j
	 * 
	 * @PersistenceContext���g�p����RESOURCE_LOCAL�^�C�v�̃��j�b�g���Q�Ƃ��邱�Ƃ͂ł��܂���
	 * EntityManger�ւ̂��ׂĂ̌Ăяo���̑O���EntityTransaction API���g�p���ĊJ�n/�R�~�b�g����K�v������܂�
	 * 
	 * entityManagerFactory.createEntityManager�i�j��2��Ăяo���ƁA 2�̕ʁX��EntityManager
	 * �C���X�^���X����������A���̂��߂�2�̕ʁX��PersistenceContext / Cache����������܂��B
	 * 
	 * EntityManager�̕����̃C���X�^���X���g�p���邱�Ƃ������߂��܂�
	 * �i���ӁF�ŏ��̃C���X�^���X��j�����Ȃ�����A2�ڂ̃C���X�^���X���쐬���Ȃ��ł��������j
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
