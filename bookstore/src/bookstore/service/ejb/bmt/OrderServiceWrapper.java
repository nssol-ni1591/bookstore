package bookstore.service.ejb.bmt;

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

	// RESOURCE_LOCAL�ł͐���ɓ��삵�Ȃ�
	//@Inject @UsedJpaLocal private BookDAO<EntityManager> bookdao
	//@Inject @UsedJpaLocal private CustomerDAO<EntityManager> customerdao
	//@Inject @UsedJpaLocal private OrderDAO<EntityManager> orderdao
	//@Inject @UsedJpaLocal private OrderDetailDAO<EntityManager> orderdetaildao
	@Inject @UsedJpaJta private BookDAO<EntityManager> bookdao;
	@Inject @UsedJpaJta private CustomerDAO<EntityManager> customerdao;
	@Inject @UsedJpaJta private OrderDAO<EntityManager> orderdao;
	@Inject @UsedJpaJta private OrderDetailDAO<EntityManager> orderdetaildao;
	@Inject private Logger log;

	// BMT�Ȃ̂Ńg�����U�N�V������UserTransaction�Ő��䂷��
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
		// BMT�ł�UserTransaction�ŊǗ�����邽�߁Aem�������p���K�v�͂Ȃ�
	}

	@Override
	public void orderBooks(String uid, List<String> inISBNs) throws Exception {
		log.log(Level.INFO, "this={0}", this);
		tx.begin();
		try {
			super.orderBooks(uid, inISBNs);
			tx.commit();
		}
		catch (Exception e) {
			tx.rollback();
			throw e;
		}
	}

}
