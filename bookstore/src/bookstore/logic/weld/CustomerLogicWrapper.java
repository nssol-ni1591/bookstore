package bookstore.logic.weld;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import bookstore.annotation.UsedWeld;
import bookstore.annotation.UsedEclipselink;
import bookstore.dao.CustomerDAO;
import bookstore.logic.AbstractCustomerLogic;

@UsedWeld
@Dependent
public class CustomerLogicWrapper extends AbstractCustomerLogic {

	@Inject @UsedEclipselink private CustomerDAO customerdao;
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
	//@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean createCustomer(String inUid, String inPassword, String inName, String inEmail) throws SQLException {
		boolean rc = super.createCustomer(inUid, inPassword, inName, inEmail);
		log.log(Level.INFO, "rc={0}", rc);
		return rc;
	}

}
