package bookstore.dao.jpa;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import bookstore.dao.CustomerDAO;
import bookstore.pbean.TCustomer;

public abstract class AbstractCustomerDAOImpl<T extends EntityManager> implements CustomerDAO<T> {
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
	public int getCustomerNumberByUid(final T em2, String uid) {
		EntityManager em = em2 != null ? em2 : getEntityManager();

		Query q = em
				.createQuery("select c from TCustomer c where c.username=:username");
		q.setParameter("username", uid);
		return q.getResultList().size();
	}

	@Override
	public TCustomer findCustomerByUid(final T em2, String uid) {
		EntityManager em = em2 != null ? em2 : getEntityManager();
		getLogger().log(Level.INFO, "em={0}", em);

		Query q = em
				.createQuery("select c from TCustomer c where c.username=:username");
		q.setParameter("username", uid);
		return (TCustomer) q.getSingleResult();
	}

	@Override
	public void saveCustomer(final T em2, String username, String passwordMD5, String name, String email) {
		EntityManager em = em2 != null ? em2 : getEntityManager();
		getLogger().log(Level.INFO, "entityManager={0}", em);

		em.getTransaction().begin();
		TCustomer customer = new TCustomer();
		customer.setUsername(username);
		customer.setPasswordmd5(passwordMD5);
		customer.setName(name);
		customer.setEmail(email);
		em.persist(customer);

		em.getTransaction().commit();
	}

}
