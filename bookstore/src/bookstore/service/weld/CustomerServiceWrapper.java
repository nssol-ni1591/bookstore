package bookstore.service.weld;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import bookstore.annotation.UsedWeld;
import bookstore.annotation.UsedJpa;
import bookstore.dao.CustomerDAO;
import bookstore.persistence.JPASelector;
import bookstore.service.AbstractCustomerService;

@UsedWeld
@Dependent
public class CustomerServiceWrapper extends AbstractCustomerService<EntityManager> {

	// JTA�ł�RESOURCE_LOCAL�ł�����ɓ��삷��iEntityTransaction���g�p���Ă���̓��R�j
	//@Inject @UsedJpaJta private CustomerDAO<EntityManager> customerdao
	//@Inject @UsedJpaLocal private CustomerDAO<EntityManager> customerdao
	@Inject @UsedJpa private CustomerDAO<EntityManager> customerdao;
	@Inject private Logger log;

	//@PersistenceUnit(name = "BookStore") private EntityManagerFactory emf
	@Inject private JPASelector selector;
	private EntityManager em = null;


	@PostConstruct
	public void init() {
		em = selector.getEntityManager();
		log.log(Level.INFO, "this={0}, em={1}", new Object[] { this, em });
	}

	@Override
	protected CustomerDAO<EntityManager> getCustomerDAO() {
		return customerdao;
	}
	@Override
	protected Logger getLogger() {
		return log;
	}
	@Override
	protected EntityManager getManager() {
		// em�͍X�VTx�̂�Service�w�Ő������邱�Ƃɂ���
		// ����āA�X�VTx�ȊO�ł�em�̒l��null�ƂȂ�̂�DAO�w�Ő��������
		return em;
	}

	@Override
	public boolean createCustomer(String uid, String password, String name, String email) throws SQLException {
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
	
			boolean rc = super.createCustomer(uid, password, name, email);
			log.log(Level.INFO, "rc={0}", rc);

			tx.commit();
			return rc;
		}
		catch (Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			throw e;
		}
		finally {
			//em.close()
			//em = null
		}
	}

}
