package bookstore.service.ejb.bmt;

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

import bookstore.annotation.UsedJpaJta;
import bookstore.dao.CustomerDAO;
import bookstore.service.AbstractCustomerService;
import bookstore.service.CustomerService;

@Stateless(name="CustomerServiceBmtWrapper")
@LocalBean
@Local(CustomerService.class)
@TransactionManagement(TransactionManagementType.BEAN)
public class CustomerServiceWrapper extends AbstractCustomerService<EntityManager> {

	// RESOURCE_LOCAL�ł͐���ɓ��삵�Ȃ�
	//@Inject @UsedJpaLocal CustomerDAO<EntityManager> customerdao
	@Inject @UsedJpaJta CustomerDAO<EntityManager> customerdao;
	@Inject private Logger log;

	// BMT�Ȃ̂Ńg�����U�N�V������UserTransaction�Ő��䂷��
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
		// BMT�ł�UserTransaction�ŊǗ�����邽�߁Aem�������p���K�v�͂Ȃ�
	}

	@Override
	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	// => The @TransactionAttribute annotation applies only to beans using Container-Managed Transactions.  
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
