package bookstore.logic.spring;

import bookstore.annotation.Log;
import bookstore.annotation.UsedSpring;
import bookstore.dao.CustomerDAO;
import bookstore.logic.AbstractCustomerLogic;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@UsedSpring
@Component("LogicCustomerImplBId")
public class CustomerLogicWrapper extends AbstractCustomerLogic {

	@Autowired @Qualifier("CustomerDAOBId") CustomerDAO customerdao;
	@Log private static Logger log;

	@Override
	protected CustomerDAO getCustomerDAO() {
		return customerdao;
	}
	@Override
	protected Logger getLogger() {
		return log;
	}

	public void setCustomerdao(CustomerDAO customerdao) {
		this.customerdao = customerdao;
	}

}