package bookstore.jsf.bean.ejbcmt;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import bookstore.jsf.bean.AbstractLoginBean;
import bookstore.service.CustomerService;
import bookstore.service.ejb.CustomerServiceLocal;

@Named
@RequestScoped
public class LoginBean3 extends AbstractLoginBean {

	//@Local
	//@EJB(mappedName="CustomerServiceCmtWrapper") private CustomerServiceLocal customerService;
	@EJB private CustomerServiceLocal customerService;


	@Override
	protected CustomerService getCustomerService() {
		return customerService;
	}

	@Override
	protected String getLoginPage() {
		return "Login3";
	}
	@Override
	protected String getBookStorePage() {
		return "BookStore3";
	}

}
