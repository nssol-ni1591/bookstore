package bookstore.logic.weld;

import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

import bookstore.annotation.UsedWeld;
import bookstore.annotation.UsedEclipselink;
import bookstore.dao.BookDAO;
import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.dao.OrderDetailDAO;
import bookstore.logic.AbstractOrderLogic;

@UsedWeld
@Dependent
public class OrderLogicWrapper extends AbstractOrderLogic<EntityManager> {

	@Inject @UsedEclipselink private BookDAO<EntityManager> bookdao;
	@Inject @UsedEclipselink private CustomerDAO<EntityManager> customerdao;
	@Inject @UsedEclipselink private OrderDAO<EntityManager> orderdao;
	@Inject @UsedEclipselink private OrderDetailDAO<EntityManager> orderdetaildao;
	@Inject private Logger log;

	@PersistenceUnit(name = "BookStore") private EntityManagerFactory emf;

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
		EntityManager em = emf.createEntityManager();
		return em;
	}

	@Override
	public void orderBooks(String inUid, List<String> inISBNs) throws Exception {
		EntityManager em = getManager();
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
	
			super.orderBooks(inUid, inISBNs);

			tx.commit();
		}
		catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			throw e;
		}
	}

}
