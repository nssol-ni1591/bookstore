package bookstore.jsf.bean.ejbeclipselink;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import bookstore.jsf.bean.AbstractLoginBean;
import bookstore.service.CustomerService;

@Named
@RequestScoped
public class LoginBean5 extends AbstractLoginBean {

	@EJB(mappedName="CustomerLogicEclipseLinkWrapper") private CustomerService customerLogic;

	public LoginBean5() {
		super();
	}

	@Override
	protected CustomerService getCustomerLogic() {
		return customerLogic;
	}

	protected String getLoginPage() {
		return "Login5";
	}
	protected String getBookStorePage() {
		return "BookStore5";
	}

}
