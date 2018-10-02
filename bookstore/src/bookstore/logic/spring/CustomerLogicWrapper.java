package bookstore.logic.spring;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import bookstore.annotation.Log;
import bookstore.annotation.UsedSpring;
import bookstore.dao.CustomerDAO;
import bookstore.logic.AbstractCustomerLogic;

@UsedSpring
@Component("LogicCustomerImplBId")
public class CustomerLogicWrapper extends AbstractCustomerLogic<SessionFactory> {

	@Log private static Logger log;

	@Autowired @Qualifier("CustomerDAOBId") CustomerDAO<SessionFactory> customerdao;
	@Autowired @Qualifier("sessionFactory") SessionFactory sessionFactory;

	@Override
	protected CustomerDAO<SessionFactory> getCustomerDAO() {
		return customerdao;
	}
	@Override
	protected Logger getLogger() {
		return log;
	}
	@Override
	protected SessionFactory getManager() {
		return sessionFactory;
	}
/*
	public void setCustomerdao(CustomerDAO<SessionFactory> customerdao) {
		this.customerdao = customerdao;
	}
*/
	@Override
	//@Transactional(propagation=Propagation.REQUIRED)
	//-> applicationContext.xmlÇ…transactionAttributesÇíËã`ÇµÇƒÇ¢ÇÈÇÃÇ≈@TransactionalÇè»ó™Ç∑ÇÈ
	public boolean createCustomer(String inUid
			, String inPassword
			, String inName
			, String inEmail) throws Exception {
		// Non-managed environment idiom
		log.log(Level.INFO, "sessionFactory={0}", sessionFactory);

		Session sess = sessionFactory.openSession();
		Transaction tx = sess.getTransaction();
		try {
			tx.begin();;
			boolean rc = super.createCustomer(inUid, inPassword, inName, inEmail);
			tx.commit();
			return rc;
		}
		catch (Exception e) {
			tx.rollback();
			throw e;
		}
		finally {
			sess.close();
		}
	}

}