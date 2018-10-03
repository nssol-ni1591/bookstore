package bookstore.dao.eclipselink;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/*
 * EntityManagerのDIに@Injectを使用する場合のProducerクラス
 * 
 * ただし、今回はEntityManagerのDIに@PersistentUnitまたは
 * @PersistentContextを使用しているので、このクラスは使用していない
 */
@Dependent
public class EntityManagerProducer {

	@Produces
	public EntityManager createEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookStore");
		return factory.createEntityManager();
	}

	public void closeEntityManager(@Disposes EntityManager entityManager) {
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}

}
