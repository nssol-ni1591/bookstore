package bookstore.service.weld;

import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

import bookstore.annotation.UsedWeld;
import bookstore.annotation.UsedJpaJta;
import bookstore.dao.BookDAO;
import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.dao.OrderDetailDAO;
import bookstore.service.AbstractOrderService;

@UsedWeld
@Dependent
public class OrderServiceWrapper extends AbstractOrderService<EntityManager> {

	@Inject @UsedJpaJta private BookDAO<EntityManager> bookdao;
	@Inject @UsedJpaJta private CustomerDAO<EntityManager> customerdao;
	@Inject @UsedJpaJta private OrderDAO<EntityManager> orderdao;
	@Inject @UsedJpaJta private OrderDetailDAO<EntityManager> orderdetaildao;
	@Inject private Logger log;

	@PersistenceUnit(name = "BookStore") private EntityManagerFactory emf;
	private EntityManager em = null;

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
		return em;
	}

	@Override
	public void orderBooks(String uid, List<String> inISBNs) throws Exception {
		em = emf.createEntityManager();
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
			em.close();
			em = null;
		}
	}

}
