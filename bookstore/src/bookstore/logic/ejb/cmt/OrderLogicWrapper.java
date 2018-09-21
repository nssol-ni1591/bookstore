package bookstore.logic.ejb.cmt;

import bookstore.annotation.UsedOpenJpa;
import bookstore.dao.BookDAO;
import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.dao.OrderDetailDAO;
import bookstore.logic.OrderLogic;
import bookstore.logic.AbstractOrderLogic;

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
import javax.persistence.PersistenceContext;

@Stateless(name="OrderLogicCmtWrapper")
@LocalBean
@Local(OrderLogic.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class OrderLogicWrapper extends AbstractOrderLogic<EntityManager> {

	@Inject @UsedOpenJpa private BookDAO<EntityManager> bookdao;
	@Inject @UsedOpenJpa private CustomerDAO<EntityManager> customerdao;
	@Inject @UsedOpenJpa private OrderDAO<EntityManager> orderdao;
	@Inject @UsedOpenJpa private OrderDetailDAO<EntityManager> orderdetaildao;
	@Inject private Logger log;

	@PersistenceContext(unitName = "BookStore2") private EntityManager em;
	// UserTransactionはBMTに対するものでCMTには利用できない
	//@Resource private UserTransaction tx;

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
	public void orderBooks(String inUid, List<String> inISBNs) throws Exception {
		try {
			log.log(Level.INFO, "this={0}", this);
			super.orderBooks(inUid, inISBNs);
		}
		catch (Exception e) {
			// EJBExceptionはシステム例外
			throw new EJBException(e);
		}
	}

}
