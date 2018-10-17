package bookstore.service.ejb;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import bookstore.annotation.UsedJpa;
import bookstore.dao.CustomerDAO;
import bookstore.persistence.JPASelector;
import bookstore.service.AbstractCustomerService;
import bookstore.service.CustomerService;

@Stateless(name="CustomerServiceEjbWrapper")
@LocalBean
@Local(CustomerService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CustomerServiceWrapper
	extends AbstractCustomerService<EntityManager>
//	implements CustomerServiceLocal, CustomerServiceRemote
{
	@Inject @UsedJpa private CustomerDAO<EntityManager> customerdao;

	@Inject private Logger log;
	@Inject private JPASelector selector;


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

	/*
	 * Container-Managed Transactionsがロールバックするのは2つの場合がある
	 * (1)システム例外が投げられた場合、コンテナは自動的にトランザクションをロールバックします
	 * (2)EJBContextインターフェイスのsetRollbackOnlyメソッドが呼び出された場合
	 * ※システム例外：java.lang.RuntimeExceptionまたはjava.rmi.RemoteException を拡張する例外
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean createCustomer(String uid
			, String password
			, String name
			, String email
			) throws SQLException
	{
		try {
			boolean rc = super.createCustomer(uid, password, name, email);
			log.log(Level.INFO, "rc={0}", rc);
			return rc;
		}
		catch (Exception e) {
			// EJBExceptionはシステム例外
			throw new EJBException(e);
		}
	}

}
