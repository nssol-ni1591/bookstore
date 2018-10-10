package bookstore.jsf.bean.ejblocal;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import bookstore.jsf.bean.AbstractLoginBean;
import bookstore.service.CustomerService;
import bookstore.service.ejb.cmt.CustomerServiceWrapper;

@Named
@RequestScoped
public class LoginBean2 extends AbstractLoginBean {

	@EJB private CustomerServiceWrapper customerService;

	public LoginBean2() {
		super();
	}

	@Override
	protected CustomerService getCustomerService() {
		return customerService;
	}

	protected String getLoginPage() {
		return "Login2";
	}
	protected String getBookStorePage() {
		return "BookStore2";
	}

}
