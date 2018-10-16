package bookstore.service.weld;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import bookstore.annotation.UsedWeld;
import bookstore.annotation.WithEntityTx;
import bookstore.annotation.WithEntityTxUpdate;
import bookstore.annotation.UsedJpa;
import bookstore.dao.CustomerDAO;
import bookstore.persistence.JPASelector;
import bookstore.service.AbstractCustomerService;
import bookstore.util.EntityTx;
import bookstore.vbean.VCustomer;

@UsedWeld
@Dependent
public class CustomerServiceWrapper extends AbstractCustomerService<EntityManager> implements EntityTx {

	@Inject @UsedJpa private CustomerDAO<EntityManager> customerdao;
	@Inject private Logger log;

	@Inject private JPASelector selector;
	// �����A���ڃv���p�e�B�̒l���X�V���Ă͂����Ȃ�
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
	public EntityManager getManager() {
		// EntityTransaction��Tx����̓s���ŁA1Tx����em�͓����C���X�^���X���g�p���Ȃ��Ƃ����Ȃ�
		log.log(Level.INFO, "em={0}", em);
		return em;
	}

	@Override
	public void startEntityTx() {
		em = selector.getEntityManager();
	}
	@Override
	public void stopEntityTx() {
		this.em = null;
	}

	@Override
	@WithEntityTxUpdate
	public boolean createCustomer(String uid, String password, String name, String email) throws SQLException {
		return super.createCustomer(uid, password, name, email);
	}

	@Override
	@WithEntityTx
	public boolean isAlreadyExsited(String uid) throws SQLException {
		return super.isAlreadyExsited(uid);
	}

	@Override
	@WithEntityTx
	public boolean isPasswordMatched(String uid, String password) throws SQLException {
		return super.isPasswordMatched(uid, password);
	}

	@Override
	@WithEntityTx
	public VCustomer createVCustomer(String uid) throws SQLException {
		return super.createVCustomer(uid);
	}

}
