package bookstore.logic.ejb;

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
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

@Stateless
@LocalBean
@Local(OrderLogic.class)
public class OrderLogicWrapper extends AbstractOrderLogic<EntityManager> {

	@Inject @UsedOpenJpa private BookDAO bookdao;
	@Inject @UsedOpenJpa private CustomerDAO customerdao;
	@Inject @UsedOpenJpa private OrderDAO<EntityManager> orderdao;
	@Inject @UsedOpenJpa private OrderDetailDAO<EntityManager> orderdetaildao;
	@Inject private Logger log;

	@PersistenceContext(unitName = "BookStore2") private EntityManager em;
	// UserTransaction‚ÍBMT‚É‘Î‚·‚é‚à‚Ì‚ÅCMT‚É‚Í—˜—p‚Å‚«‚È‚¢
	//@Resource private UserTransaction tx;

	@Override
	protected BookDAO getBookDAO() {
		return bookdao;
	}
	@Override
	protected CustomerDAO getCustomerDAO() {
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

	@Override
	public void orderBooks(String inUid, List<String> inISBNs) throws Exception {
		/*
		try {
			tx.begin();
			super.orderBooks(inUid, inISBNs);
			tx.commit();
		}
		catch (Exception e) {
			tx.rollback();
		}
		*/
		super.orderBooks(inUid, inISBNs);
	}

}
