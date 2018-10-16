package bookstore.service.weld;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import bookstore.annotation.UsedWeld;
import bookstore.annotation.UsedJpa;
import bookstore.dao.CustomerDAO;
import bookstore.persistence.JPASelector;
import bookstore.service.AbstractCustomerService;
import bookstore.vbean.VCustomer;

@UsedWeld
@Dependent
public class CustomerServiceWrapper extends AbstractCustomerService<EntityManager> {

	@Inject @UsedJpa private CustomerDAO<EntityManager> customerdao;
	@Inject private Logger log;

	@Inject private JPASelector selector;
	// 多分、直接プロパティの値を更新してはいけない
	private EntityManager em = null;


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
		// EntityTransactionのTx制御の都合で、1Tx内のemは同じインスタンスを使用しないといけない
		log.log(Level.INFO, "em={0}", em);
		return em;
	}

	private void startManager() {
		em = selector.getEntityManager();
		log.log(Level.INFO, "em={0}", em);
		if (em == null) {
			log.log(Level.INFO, "print stack trace", new Exception());
		}
	}
	private void stopManager() {
		this.em = null;
	}

	@Override
	public boolean createCustomer(String uid, String password, String name, String email) throws SQLException {
		startManager();
		EntityTransaction tx = null;
		try {
			tx = getManager().getTransaction();
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
			stopManager();
		}
	}

	@Override
	public boolean isAlreadyExsited(String uid) throws SQLException {
		startManager();
		boolean rc = super.isAlreadyExsited(uid);
		stopManager();
		return rc;
	}

	@Override
	public boolean isPasswordMatched(String uid, String password) throws SQLException {
		startManager();
		log.log(Level.INFO, "em={0}", em);
		boolean rc = super.isPasswordMatched(uid, password);
		stopManager();
		return rc;
	}

	@Override
	public VCustomer createVCustomer(String uid) throws SQLException {
		startManager();
		VCustomer vc = super.createVCustomer(uid);
		stopManager();
		return vc;
	}

}
