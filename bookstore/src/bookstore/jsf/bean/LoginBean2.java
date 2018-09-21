package bookstore.jsf.bean;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import bookstore.logic.CustomerLogic;
import bookstore.logic.ejb.cmt.CustomerLogicWrapper;

@Named
@RequestScoped
public class LoginBean2 extends AbstractLoginBean {

	@EJB private CustomerLogicWrapper customerLogic;

	public LoginBean2() {
		super();
	}

	@Override
	protected CustomerLogic getCustomerLogic() {
		return customerLogic;
	}

	protected String getLoginPage() {
		return "Login2";
	}
	protected String getBookStorePage() {
		return "BookStore2";
	}

}
