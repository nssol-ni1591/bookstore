package bookstore.jsf.bean;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import bookstore.logic.CustomerLogic;

@Named(value="loginBean3")
@RequestScoped
public class LoginBean3 extends AbstractLoginBean {

	@EJB private CustomerLogic customerLogic;

	public LoginBean3() {
		super();
	}

	@Override
	protected CustomerLogic getCustomerLogic() {
		return customerLogic;
	}

	protected String getLoginPage() {
		return "Login3";
	}
	protected String getBookStorePage() {
		return "BookStore3";
	}

}
