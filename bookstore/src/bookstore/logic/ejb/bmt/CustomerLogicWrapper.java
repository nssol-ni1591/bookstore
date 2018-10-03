package bookstore.logic.ejb.bmt;

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

import bookstore.annotation.UsedJpaLocal;
import bookstore.dao.CustomerDAO;
import bookstore.logic.CustomerLogic;
import bookstore.logic.AbstractCustomerLogic;

@Stateless(name="CustomerLogicBmtWrapper")
@LocalBean
@Local(CustomerLogic.class)
@TransactionManagement(TransactionManagementType.BEAN)
public class CustomerLogicWrapper extends AbstractCustomerLogic<EntityManager> {

	@Inject @UsedJpaLocal CustomerDAO<EntityManager> customerdao;
	@Inject private Logger log;

	// UserTransactionはBMTに対するものでCMTには利用できない
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
		return null;
		// BMTではUserTransactionで管理されるため、emを引き継ぐ必要はなし
	}

	@Override
	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	// => The @TransactionAttribute annotation applies only to beans using Container-Managed Transactions.  
	public boolean createCustomer(String inUid, String inPassword, String inName, String inEmail) throws Exception {
		log.log(Level.INFO, "this={0}", this);
		try {
			tx.begin();
			boolean rc = super.createCustomer(inUid, inPassword, inName, inEmail);
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
