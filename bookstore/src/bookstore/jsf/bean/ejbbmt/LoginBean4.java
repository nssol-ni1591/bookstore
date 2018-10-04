package bookstore.jsf.bean.ejbbmt;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import bookstore.jsf.bean.AbstractLoginBean;
import bookstore.service.CustomerService;

@Named
@RequestScoped
public class LoginBean4 extends AbstractLoginBean {

	@EJB(mappedName="CustomerServiceBmtWrapper") private CustomerService customerService;

	public LoginBean4() {
		super();
	}

	@Override
	protected CustomerService getCustomerService() {
		return customerService;
	}

	protected String getLoginPage() {
		return "Login4";
	}
	protected String getBookStorePage() {
		return "BookStore4";
	}

}
