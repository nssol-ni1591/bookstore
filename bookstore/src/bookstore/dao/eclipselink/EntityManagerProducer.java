package bookstore.dao.eclipselink;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/*
 * EntityManager��DI��@Inject���g�p����ꍇ��Producer�N���X
 * 
 * �������A�����EntityManager��DI��@PersistentUnit�܂���
 * @PersistentContext���g�p���Ă���̂ŁA���̃N���X�͎g�p���Ă��Ȃ�
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
