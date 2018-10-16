package bookstore.service.weld;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import bookstore.annotation.UsedWeld;
import bookstore.annotation.UsedJpa;
import bookstore.dao.BookDAO;
import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.dao.OrderDetailDAO;
import bookstore.persistence.JPASelector;
import bookstore.service.AbstractOrderService;
import bookstore.vbean.VOrder;

@UsedWeld
@Dependent
public class OrderServiceWrapper extends AbstractOrderService<EntityManager> {

	@Inject @UsedJpa private BookDAO<EntityManager> bookdao;
	@Inject @UsedJpa private CustomerDAO<EntityManager> customerdao;
	@Inject @UsedJpa private OrderDAO<EntityManager> orderdao;
	@Inject @UsedJpa private OrderDetailDAO<EntityManager> orderdetaildao;
	@Inject private Logger log;

	private EntityManager em = null;
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
		// EntityTransactionのTx制御の都合で、1Tx内のemは同じインスタンスを使用しないといけない
		return em;
	}

	private void startManager() {
		em = selector.getEntityManager();
		log.log(Level.INFO, "em={0}", em);
		if (em == null) {
			log.log(Level.INFO, "print stack trace", new Exception());
		}
	}
	private void stopManager() {
		this.em = null;
	}

	@Override
	public void orderBooks(String uid, List<String> isbns) throws SQLException {
		startManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
	
			super.orderBooks(uid, isbns);

			tx.commit();
		}
		catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw e;
		}
		finally {
			stopManager();
		}
	}

	@Override
	public List<VOrder> listOrders(List<String> orderIdList) throws SQLException {
		startManager();
		List<VOrder> list = super.listOrders(orderIdList);
		stopManager();
		return list;
	}

}
