package bookstore.jsf.bean.eclipselink;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import bookstore.annotation.UsedWeld;
import bookstore.jsf.bean.AbstractLoginBean;
import bookstore.service.CustomerService;

@Named
@RequestScoped
public class LoginBean extends AbstractLoginBean {

	@Inject @UsedWeld private CustomerService customerLogic;

	public LoginBean() {
		super();
	}

	@Override
	protected CustomerService getCustomerLogic() {
		return customerLogic;
	}

	protected String getLoginPage() {
		return "Login";
	}
	protected String getBookStorePage() {
		return "BookStore";
	}

}
