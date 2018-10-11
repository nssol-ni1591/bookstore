package bookstore.service.ejb;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import bookstore.annotation.UsedJpa;
import bookstore.dao.CustomerDAO;
import bookstore.persistence.JPASelector;
import bookstore.service.AbstractCustomerService;
import bookstore.service.CustomerService;

@Stateless(name="CustomerServiceEjbBmtWrapper")
@LocalBean
@Local(CustomerService.class)
@TransactionManagement(TransactionManagementType.BEAN)
public class CustomerServiceWrapper6 extends AbstractCustomerService<EntityManager> {

	@Inject @UsedJpa private CustomerDAO<EntityManager> customerdao;

	@Inject private Logger log;
	@Inject private JPASelector selector;

	// BMTなのでトランザクションをUserTransactionで制御する
	@Resource private UserTransaction tx;


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
		EntityManager em = selector.getEntityManager();
		log.log(Level.INFO, "this={0}, em={1}", new Object[] { this, em });
		return em;
	}

	@Override
	public boolean createCustomer(String uid, String password, String name, String email) throws Exception {
		log.log(Level.INFO, "this={0}", this);
		try {
			tx.begin();
			boolean rc = super.createCustomer(uid, password, name, email);
			log.log(Level.INFO, "rc={0}", rc);
			tx.commit();
			return rc;
		}
		catch (Exception e) {
			tx.rollback();
			throw e;
		}
	}

}
