package bookstore.logic.spring;

import bookstore.annotation.Log;
import bookstore.annotation.UsedSpring;
import bookstore.dao.CustomerDAO;
import bookstore.logic.impl.CustomerLogicImpl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@UsedSpring
@Component("LogicCustomerImplBId")
public class CustomerLogicWrapper extends CustomerLogicImpl {

	@Log private static Logger log;

	@Autowired @Qualifier("CustomerDAOBId") CustomerDAO customerdao;

	@Override
	public void setCustomerdao(CustomerDAO inCdao) {
		if (log != null)
			log.log(Level.INFO, "customerdao={0}", customerdao);
		this.customerdao = inCdao;
		super.setCustomerdao(customerdao);
	}

	@Override
	protected Logger getLogger() {
		return log;
	}

}