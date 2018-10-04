package bookstore.jsf.bean.ejbcmt;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import bookstore.jsf.bean.AbstractLoginBean;
import bookstore.service.CustomerService;

@Named
@RequestScoped
public class LoginBean3 extends AbstractLoginBean {

	@EJB(mappedName="CustomerServiceBmtWrapper") private CustomerService customerService;

	public LoginBean3() {
		super();
	}

	@Override
	protected CustomerService getCustomerService() {
		return customerService;
	}

	protected String getLoginPage() {
		return "Login3";
	}
	protected String getBookStorePage() {
		return "BookStore3";
	}

}
