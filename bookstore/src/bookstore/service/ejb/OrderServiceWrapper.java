package bookstore.service.ejb;

import java.sql.SQLException;
import java.util.List;
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
import bookstore.dao.BookDAO;
import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.dao.OrderDetailDAO;
import bookstore.persistence.JPASelector;
import bookstore.service.AbstractOrderService;
import bookstore.service.OrderService;

@Stateless(name="OrderServiceEjbWrapper")
@LocalBean
@Local(OrderService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class OrderServiceWrapper
	extends AbstractOrderService<EntityManager>
//	implements OrderServiceLocal, OrderServiceRemote
{
	@Inject @UsedJpa private BookDAO<EntityManager> bookdao;
	@Inject @UsedJpa private CustomerDAO<EntityManager> customerdao;
	@Inject @UsedJpa private OrderDAO<EntityManager> orderdao;
	@Inject @UsedJpa private OrderDetailDAO<EntityManager> orderdetaildao;

	@Inject private Logger log;
	@Inject private JPASelector selector;


	@Override
	protected BookDAO<EntityManager> getBookDAO() {
		return bookdao;
	}
	@Override
	protected CustomerDAO<EntityManager> getCustomerDAO() {
		return customerdao;
	}
	@Override
	protected OrderDAO<EntityManager> getOrderDAO() {
		return orderdao;
	}
	@Override
	protected OrderDetailDAO<EntityManager> getOrderDetailDAO() {
		return orderdetaildao;
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
	public void orderBooks(String uid, List<String> isbns) throws SQLException {
		try {
			log.log(Level.INFO, "this={0}", this);
			super.orderBooks(uid, isbns);
		}
		catch (RuntimeException /*| RemoteException*/ e) {
			throw e;
		}
		catch (Exception e) {
			// EJBExceptionはシステム例外
			throw new EJBException(e);
		}
	}

}
