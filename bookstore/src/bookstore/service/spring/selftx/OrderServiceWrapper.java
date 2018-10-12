package bookstore.service.spring.selftx;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import bookstore.annotation.Log;
import bookstore.annotation.UsedSpring;
import bookstore.dao.BookDAO;
import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.dao.OrderDetailDAO;
import bookstore.service.AbstractOrderService;

/*
 * SessionFactory���g�p���Ď�����Tx������s������
 * ���ʁF�����Tx�𔭐������邱�Ƃ��ł��Ȃ�����
 * 
 * ���Ȃ݂ɁASessionfactory��nested_Tx���T�|�[�g���Ă��Ȃ��炵���̂�
 * ������Tx���䂪�ł����Ƃ��Ă����p���@���Ȃ�
 */
@UsedSpring
@Component("ServiceOrderImplBId_Self")
public class OrderServiceWrapper extends AbstractOrderService<SessionFactory> {

	@Log private static Logger log;

	@Autowired @Qualifier("BookDAOBId") BookDAO<SessionFactory> bookdao;
	@Autowired @Qualifier("CustomerDAOBId") CustomerDAO<SessionFactory> customerdao;
	@Autowired @Qualifier("OrderDAOBId") OrderDAO<SessionFactory> orderdao;
	@Autowired @Qualifier("OrderDetailDAOBId") OrderDetailDAO<SessionFactory> odetaildao;
	@Autowired SessionFactory sessionFactory;

	@Override
	protected BookDAO<SessionFactory> getBookDAO() {
		return bookdao;
	}
	@Override
	protected CustomerDAO<SessionFactory> getCustomerDAO() {
		return customerdao;
	}
	@Override
	protected OrderDAO<SessionFactory> getOrderDAO() {
		return orderdao;
	}
	@Override
	protected OrderDetailDAO<SessionFactory> getOrderDetailDAO() {
		return odetaildao;
	}
	@Override
	protected Logger getLogger() {
		return log;
	}
	@Override
	protected SessionFactory getManager() {
		// ������null���w�肵��DAO�����N���X����DI����sessionFactory���g�p���Ă�
		// Tx����ɈႢ�͂Ȃ��B�炵��
		//return sessionFactory
		return null;
	}

	// ������Tx������s��
	@Override
	public void orderBooks(String uid, List<String> isbns) throws SQLException {
		// Non-managed environment idiom

		Session sess = sessionFactory.openSession();
		//��org.hibernate.TransactionException: nested transactions not supported

		//Session sess = sessionFactory.getCurrentSession()
		//��org.hibernate.HibernateException: Could not obtain transaction-synchronized Session for current thread
		Transaction tx = sess.beginTransaction();
		try {
			tx.begin();
			super.orderBooks(uid, isbns);
			tx.commit();
		}
		catch (Exception e) {
			tx.rollback();
			throw e;
		}
	}

}
