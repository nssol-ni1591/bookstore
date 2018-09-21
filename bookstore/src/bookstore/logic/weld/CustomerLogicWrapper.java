package bookstore.logic.weld;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import bookstore.annotation.UsedWeld;
import bookstore.annotation.UsedEclipselink;
import bookstore.dao.CustomerDAO;
import bookstore.logic.AbstractCustomerLogic;

@UsedWeld
@Dependent
public class CustomerLogicWrapper extends AbstractCustomerLogic<EntityManager> {

	@Inject @UsedEclipselink private CustomerDAO<EntityManager> customerdao;
	@Inject private Logger log;

	//@PersistenceContext(unitName = "BookStore") private EntityManager em;
	@PersistenceUnit(name = "BookStore") private EntityManagerFactory emf;

	@Override
	protected CustomerDAO<EntityManager> getCustomerDAO() {
		return customerdao;
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
	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean createCustomer(String inUid, String inPassword, String inName, String inEmail) throws Exception {
		boolean rc = super.createCustomer(inUid, inPassword, inName, inEmail);
		log.log(Level.INFO, "rc={0}", rc);
		return rc;
	}

}
