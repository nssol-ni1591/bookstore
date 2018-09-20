package bookstore.logic.ejb;

import bookstore.annotation.UsedOpenJpa;
import bookstore.dao.CustomerDAO;
import bookstore.logic.CustomerLogic;
import bookstore.logic.AbstractCustomerLogic;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

@Stateless
@LocalBean
@Local(CustomerLogic.class)
public class CustomerLogicWrapper extends AbstractCustomerLogic {

	@Inject @UsedOpenJpa CustomerDAO customerdao;
	@Inject private Logger log;

	@Override
	protected CustomerDAO getCustomerDAO() {
		return customerdao;
	}
	@Override
	protected Logger getLogger() {
		return log;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean createCustomer(String inUid, String inPassword, String inName, String inEmail) throws SQLException {
		boolean rc = super.createCustomer(inUid, inPassword, inName, inEmail);
		log.log(Level.INFO, "rc={0}", rc);
		return rc;
	}

}
