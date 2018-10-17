package bookstore.service.ejb.cmt;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import bookstore.annotation.UsedCMT;
import bookstore.annotation.UsedJpaJta;
import bookstore.dao.BookDAO;
import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.dao.OrderDetailDAO;
import bookstore.service.AbstractOrderService;
import bookstore.service.OrderService;
import bookstore.service.ejb.OrderServiceLocal;
import bookstore.service.ejb.OrderServiceRemote;

@UsedCMT	// 無意味
@Stateless(name="OrderServiceCmtWrapper")
@LocalBean
@Local(OrderService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class OrderServiceWrapper
	extends AbstractOrderService<EntityManager>
	implements OrderServiceLocal, OrderServiceRemote
{
	@Inject @UsedJpaJta private BookDAO<EntityManager> bookdao;
	@Inject @UsedJpaJta private CustomerDAO<EntityManager> customerdao;
	@Inject @UsedJpaJta private OrderDAO<EntityManager> orderdao;
	@Inject @UsedJpaJta private OrderDetailDAO<EntityManager> orderdetaildao;
	@Inject private Logger log;

	@Resource SessionContext ctx;


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
		return null;
		// @TransactionAttributeで管理されるため、emを引き継ぐ必要はなし
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
		log.log(Level.INFO, "this={0}", this);
		try {
			super.orderBooks(uid, isbns);
		}
		catch (RuntimeException /*| RemoteException*/ e) {
			throw e;
		}
		catch (Exception e) {
			// EJBExceptionはシステム例外
			//throw new EJBException(e)
			ctx.setRollbackOnly();
			throw e;
		}
	}

}
