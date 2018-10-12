package bookstore.service.weld;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
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

	@Inject @UsedJpa private BookDAO<EntityManager> bookdao;
	@Inject @UsedJpa private CustomerDAO<EntityManager> customerdao;
	@Inject @UsedJpa private OrderDAO<EntityManager> orderdao;
	@Inject @UsedJpa private OrderDetailDAO<EntityManager> orderdetaildao;
	@Inject private Logger log;

	private EntityManager em = null;
	@Inject private JPASelector selector;


	@PostConstruct
	public void init() {
		em = selector.getEntityManager();
		log.log(Level.INFO, "this={0}, em={1}", new Object[] { this, em });
	}

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
		// EntityTransaction��Tx����̓s���ŁA1Tx����em�͓����C���X�^���X���g�p���Ȃ��Ƃ����Ȃ�
		// �����ł́A���̃N���X�̃C���X�^���X�Ŏg�p����em�͓����C���X�^���X���Q�Ƃ��邱�ƂőΉ�����
		return em;
		// ������A�����ɕ����̃X���b�h�ł��̃C���X�^���X���g�p�����Ɠs��������
	}

	@Override
	public void orderBooks(String uid, List<String> isbns) throws SQLException {
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
	
			super.orderBooks(uid, isbns);

			tx.commit();
		}
		catch (Exception e) {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			throw e;
		}
		finally {
			// Do nothing.
		}
	}

}
