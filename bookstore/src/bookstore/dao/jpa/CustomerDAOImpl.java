package bookstore.dao.jpa;

import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import bookstore.annotation.UsedJpa;

@UsedJpa
@Dependent
public class CustomerDAOImpl<T extends EntityManager> extends AbstractCustomerDAOImpl<T> {

	@Inject private Logger log; 

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
	@Override
	protected EntityManager getEntityManager() {
		return null;
	}

	@Override
	protected Logger getLogger() {
		return log;
	}

}
