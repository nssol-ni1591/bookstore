package bookstore.dao.jpa;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import bookstore.dao.CustomerDAO;
import bookstore.pbean.TCustomer;

public abstract class AbstractCustomerDAOImpl<T extends EntityManager> implements CustomerDAO<T> {
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

	@Inject private Logger log;

	@Override
	public int getCustomerNumberByUid(final T em2, String inUid) {
		EntityManager em = em2 != null ? em2 : getEntityManager();

		Query q = em
				.createQuery("select c from TCustomer c where c.username=:username");
		q.setParameter("username", inUid);
		return q.getResultList().size();
	}

	@Override
	public TCustomer findCustomerByUid(final T em2, String inUid) {
		EntityManager em = em2 != null ? em2 : getEntityManager();

		Query q = em
				.createQuery("select c from TCustomer c where c.username=:username");
		q.setParameter("username", inUid);
		return (TCustomer) q.getSingleResult();
	}

	@Override
	public void saveCustomer(final T em2, String inUsername, String inPasswordMD5, String inName, String inEmail) {
		EntityManager em = em2 != null ? em2 : getEntityManager();
		log.log(Level.INFO, "entityManager={0}", em);

		em.getTransaction().begin();
		TCustomer customer = new TCustomer();
		customer.setUsername(inUsername);
		customer.setPasswordmd5(inPasswordMD5);
		customer.setName(inName);
		customer.setEmail(inEmail);
		em.persist(customer);

		em.getTransaction().commit();
	}

}
