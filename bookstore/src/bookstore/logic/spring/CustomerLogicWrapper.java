package bookstore.logic.spring;

import bookstore.annotation.UsedSpring;
import bookstore.dao.CustomerDAO;
import bookstore.logic.impl.CustomerLogicImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@UsedSpring
@Component("LogicCustomerImplBId")
public class CustomerLogicWrapper extends CustomerLogicImpl {

	@Autowired @Qualifier("CustomerDAOBId") CustomerDAO customerdao;

	@Override
	public void setCustomerdao(CustomerDAO inCdao) {
		System.out.println("spring.CustomerLogicWrapper.setCustomerdao: customerdao=" + customerdao);
		this.customerdao = inCdao;
		super.setCustomerdao(customerdao);
	}

}