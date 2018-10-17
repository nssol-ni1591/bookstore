package bookstore.jsf.bean.ejblocal;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import bookstore.jsf.bean.AbstractLoginBean;
import bookstore.service.CustomerService;

@Named
@RequestScoped
public class LoginBean2 extends AbstractLoginBean {

	//@LocalBean
	@EJB private CustomerService customerService;
	// @Local
	//@EJB private CustomerServiceLocal customerService
	// @Remote
	//@EJB private CustomerServiceRemote customerService


	@Override
	protected CustomerService getCustomerService() {
		return customerService;
	}

	@Override
	protected String getLoginPage() {
		return "Login2";
	}
	@Override
	protected String getBookStorePage() {
		return "BookStore2";
	}

}
