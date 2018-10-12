package bookstore.service.ejb.bmt;

import java.sql.SQLException;
import java.util.List;
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

import bookstore.annotation.UsedJpaJta;
import bookstore.dao.BookDAO;
import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.dao.OrderDetailDAO;
import bookstore.service.AbstractOrderService;
import bookstore.service.OrderService;

@Stateless(name="OrderServiceBmtWrapper")
@LocalBean
@Local(OrderService.class)
@TransactionManagement(TransactionManagementType.BEAN)
public class OrderServiceWrapper extends AbstractOrderService<EntityManager> {

	// RESOURCE_LOCALでは正常に動作しない
	//@Inject @UsedJpaLocal private BookDAO<EntityManager> bookdao
	//@Inject @UsedJpaLocal private CustomerDAO<EntityManager> customerdao
	//@Inject @UsedJpaLocal private OrderDAO<EntityManager> orderdao
	//@Inject @UsedJpaLocal private OrderDetailDAO<EntityManager> orderdetaildao
	@Inject @UsedJpaJta private BookDAO<EntityManager> bookdao;
	@Inject @UsedJpaJta private CustomerDAO<EntityManager> customerdao;
	@Inject @UsedJpaJta private OrderDAO<EntityManager> orderdao;
	@Inject @UsedJpaJta private OrderDetailDAO<EntityManager> orderdetaildao;
	@Inject private Logger log;

	// BMTなのでトランザクションをUserTransactionで制御する
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
	public void orderBooks(String uid, List<String> isbns) throws SQLException {
		log.log(Level.INFO, "this={0}", this);
		try {
			tx.begin();
			super.orderBooks(uid, isbns);
			tx.commit();
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
