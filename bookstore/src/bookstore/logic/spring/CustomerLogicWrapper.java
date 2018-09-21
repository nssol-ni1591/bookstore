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
public class CustomerLogicWrapper extends AbstractCustomerLogic<Object> {

	@Autowired @Qualifier("CustomerDAOBId") CustomerDAO<Object> customerdao;
	@Log private static Logger log;

	@Override
	protected CustomerDAO<Object> getCustomerDAO() {
		return customerdao;
	}
	@Override
	protected Logger getLogger() {
		return log;
	}
	@Override
	protected Object getManager() {
		return null;
	}

	public void setCustomerdao(CustomerDAO<Object> customerdao) {
		this.customerdao = customerdao;
	}

}