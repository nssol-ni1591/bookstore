package bookstore.service.ejb.bmt;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import bookstore.annotation.UsedBMT;
import bookstore.annotation.UsedJpaJta;
import bookstore.dao.CustomerDAO;
import bookstore.service.AbstractCustomerService;
import bookstore.service.CustomerService;
import bookstore.service.ejb.CustomerServiceLocal;
import bookstore.service.ejb.CustomerServiceRemote;

@UsedBMT	// 無意味
@Stateless//(name="CustomerServiceBmtWrapper")
@LocalBean
@Local(CustomerService.class)
@TransactionManagement(TransactionManagementType.BEAN)
public class CustomerServiceWrapper
	extends AbstractCustomerService<EntityManager>
	implements CustomerServiceLocal, CustomerServiceRemote
{
	// RESOURCE_LOCALでは正常に動作しない
	//@Inject @UsedJpaLocal CustomerDAO<EntityManager> customerdao
	@Inject @UsedJpaJta CustomerDAO<EntityManager> customerdao;
	@Inject private Logger log;

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
		return null;
		// BMTではUserTransactionで管理されるため、emを引き継ぐ必要はなし
	}

	@Override
	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	// => The @TransactionAttribute annotation applies only to beans using Container-Managed Transactions.  
	public boolean createCustomer(String uid
			, String password
			, String name
			, String email
			) throws SQLException
	{
		log.log(Level.INFO, "this={0}", this);
		try {
			tx.begin();
			boolean rc = super.createCustomer(uid, password, name, email);
			log.log(Level.INFO, "rc={0}", rc);
			tx.commit();
			return rc;
		}
		catch (Exception e) {
			try {
				tx.rollback();
			}
			catch (Exception e2) {
				log.log(Level.SEVERE, "in rollback", e2);
			}
			throw new EJBException(e);
		}
	}

}
