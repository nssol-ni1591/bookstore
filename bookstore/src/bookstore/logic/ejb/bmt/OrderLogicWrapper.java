package bookstore.logic.ejb.bmt;

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

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

@Stateless(name="OrderLogicBmtWrapper")
@LocalBean
@Local(OrderLogic.class)
@TransactionManagement(TransactionManagementType.BEAN)
public class OrderLogicWrapper extends AbstractOrderLogic<EntityManager> {

	@Inject @UsedOpenJpa private BookDAO<EntityManager> bookdao;
	@Inject @UsedOpenJpa private CustomerDAO<EntityManager> customerdao;
	@Inject @UsedOpenJpa private OrderDAO<EntityManager> orderdao;
	@Inject @UsedOpenJpa private OrderDetailDAO<EntityManager> orderdetaildao;
	@Inject private Logger log;

	// UserTransactionはBMTに対するものでCMTには利用できない
	@Resource private UserTransaction tx;

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
		// BMTではUserTransactionで管理されるため、emを引き継ぐ必要はなし
	}

	@Override
	public void orderBooks(String inUid, List<String> inISBNs) throws Exception {
		log.log(Level.INFO, "this={0}", this);
		try {
			tx.begin();
			super.orderBooks(inUid, inISBNs);
			tx.commit();
		}
		catch (Exception e) {
			tx.rollback();
			throw e;
		}
	}

}
