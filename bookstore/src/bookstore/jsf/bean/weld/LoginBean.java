package bookstore.jsf.bean.weld;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import bookstore.annotation.UsedWeld;
import bookstore.jsf.bean.AbstractLoginBean;
import bookstore.service.CustomerService;

@Named
@RequestScoped
public class LoginBean extends AbstractLoginBean {

	@Inject @UsedWeld private CustomerService customerService;

	public LoginBean() {
		super();
	}

	@Override
	protected CustomerService getCustomerService() {
		return customerService;
	}

	protected String getLoginPage() {
		return "Login";
	}
	protected String getBookStorePage() {
		return "BookStore";
	}

}
