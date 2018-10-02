package bookstore.jsf.bean.ejbbmt;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import bookstore.jsf.bean.AbstractLoginBean;
import bookstore.logic.CustomerLogic;

@Named
@RequestScoped
public class LoginBean4 extends AbstractLoginBean {

	@EJB(mappedName="CustomerLogicBmtWrapper") private CustomerLogic customerLogic;

	public LoginBean4() {
		super();
	}

	@Override
	protected CustomerLogic getCustomerLogic() {
		return customerLogic;
	}

	protected String getLoginPage() {
		return "Login4";
	}
	protected String getBookStorePage() {
		return "BookStore4";
	}

}
