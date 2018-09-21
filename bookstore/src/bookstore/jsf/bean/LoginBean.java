package bookstore.jsf.bean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import bookstore.annotation.UsedWeld;
import bookstore.logic.CustomerLogic;

@Named
@RequestScoped
public class LoginBean extends AbstractLoginBean {

	@Inject @UsedWeld private CustomerLogic customerLogic;

	public LoginBean() {
		super();
	}

	@Override
	protected CustomerLogic getCustomerLogic() {
		return customerLogic;
	}

	protected String getLoginPage() {
		return "Login";
	}
	protected String getBookStorePage() {
		return "BookStore";
	}

}
