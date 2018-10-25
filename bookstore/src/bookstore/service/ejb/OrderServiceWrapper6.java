package bookstore.service.ejb;

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

import bookstore.annotation.UsedJpaSelector;
import bookstore.dao.BookDAO;
import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.dao.OrderDetailDAO;
import bookstore.persistence.JPASelector;
import bookstore.service.AbstractOrderService;
import bookstore.service.OrderService;

@Stateless(name="OrderServiceEjbBmtWrapper")
@LocalBean
@Local(OrderService.class)
@TransactionManagement(TransactionManagementType.BEAN)
public class OrderServiceWrapper6
	extends AbstractOrderService<EntityManager>
//	implements OrderServiceLocal, OrderServiceRemote
{
	@Inject @UsedJpaSelector private BookDAO<EntityManager> bookdao;
	@Inject @UsedJpaSelector private CustomerDAO<EntityManager> customerdao;
	@Inject @UsedJpaSelector private OrderDAO<EntityManager> orderdao;
	@Inject @UsedJpaSelector private OrderDetailDAO<EntityManager> orderdetaildao;

	@Inject private Logger log;
	@Inject private JPASelector selector;

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
		EntityManager em = selector.getEntityManager();
		log.log(Level.INFO, "this={0}, em={1}", new Object[] { this, em });
		return em;
	}

	@Override
	public void orderBooks(String uid, List<String> isbns) throws SQLException {
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
