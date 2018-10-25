package bookstore.service.weld;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import bookstore.annotation.UsedWeld;
import bookstore.annotation.WithEntityTx;
import bookstore.annotation.WithEntityTxUpdate;
import bookstore.annotation.UsedJpaSelector;
import bookstore.dao.BookDAO;
import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.dao.OrderDetailDAO;
import bookstore.persistence.JPASelector;
import bookstore.service.AbstractOrderService;
import bookstore.util.EntityTx;
import bookstore.vbean.VOrder;

@UsedWeld
@Dependent
public class OrderServiceWrapper extends AbstractOrderService<EntityManager> implements EntityTx {

	@Inject @UsedJpaSelector private BookDAO<EntityManager> bookdao;
	@Inject @UsedJpaSelector private CustomerDAO<EntityManager> customerdao;
	@Inject @UsedJpaSelector private OrderDAO<EntityManager> orderdao;
	@Inject @UsedJpaSelector private OrderDetailDAO<EntityManager> orderdetaildao;
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
	public EntityManager getManager() {
		// EntityTransactionのTx制御の都合で、1Tx内のemは同じインスタンスを使用しないといけない
		return em;
	}

	@Override
	public void startEntityTx() {
		em = selector.getEntityManager();
	}
	@Override
	public void stopEntityTx() {
		this.em = null;
	}

	@Override
	@WithEntityTxUpdate
	public void orderBooks(String uid, List<String> isbns) throws SQLException {
		super.orderBooks(uid, isbns);
	}

	@Override
	@WithEntityTx
	public List<VOrder> listOrders(List<String> orderIdList) throws SQLException {
		return super.listOrders(orderIdList);
	}

}
