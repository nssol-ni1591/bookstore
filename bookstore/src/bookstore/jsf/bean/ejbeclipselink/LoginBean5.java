package bookstore.jsf.bean.ejbeclipselink;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import bookstore.jsf.bean.AbstractLoginBean;
import bookstore.logic.CustomerLogic;

@Named
@RequestScoped
public class LoginBean5 extends AbstractLoginBean {

	@EJB(mappedName="CustomerLogicEclipseLinkWrapper") private CustomerLogic customerLogic;

	public LoginBean5() {
		super();
	}

	@Override
	protected CustomerLogic getCustomerLogic() {
		return customerLogic;
	}

	protected String getLoginPage() {
		return "Login5";
	}
	protected String getBookStorePage() {
		return "BookStore5";
	}

}
