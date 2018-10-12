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

@UsedWeld
@Dependent
public class OrderServiceWrapper extends AbstractOrderService<EntityManager> {

	// JTAでもRESOURCE_LOCALでも正常に動作する（EntityTransactionを使用しているの当然）
	//@Inject @UsedJpaJta private BookDAO<EntityManager> bookdao
	//@Inject @UsedJpaJta private CustomerDAO<EntityManager> customerdao
	//@Inject @UsedJpaJta private OrderDAO<EntityManager> orderdao
	//@Inject @UsedJpaJta private OrderDetailDAO<EntityManager> orderdetaildao
	//@Inject @UsedJpaLocal private BookDAO<EntityManager> bookdao
	//@Inject @UsedJpaLocal private CustomerDAO<EntityManager> customerdao
	//@Inject @UsedJpaLocal private OrderDAO<EntityManager> orderdao
	//@Inject @UsedJpaLocal private OrderDetailDAO<EntityManager> orderdetaildao
	@Inject @UsedJpa private BookDAO<EntityManager> bookdao;
	@Inject @UsedJpa private CustomerDAO<EntityManager> customerdao;
	@Inject @UsedJpa private OrderDAO<EntityManager> orderdao;
	@Inject @UsedJpa private OrderDetailDAO<EntityManager> orderdetaildao;
	@Inject private Logger log;

	//@PersistenceUnit(name = "BookStore") private EntityManagerFactory emf
	//private EntityManager em = null
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
		// emは更新TxのみService層で生成することにする
		// よって、更新Tx以外ではemの値はnullとなるのでDAO層で生成される
		//return em
		EntityManager em = selector.getEntityManager();
		log.log(Level.INFO, "this={0}, em={1}", new Object[] { this, em });
		return em;
	}

	@Override
	public void orderBooks(String uid, List<String> inISBNs) throws SQLException {
		//em = emf.createEntityManager()
		EntityManager em = getManager();
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
	
			super.orderBooks(uid, inISBNs);

			tx.commit();
		}
		catch (Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			throw e;
		}
		finally {
			//em.close()
			//em = null
		}
	}

}
