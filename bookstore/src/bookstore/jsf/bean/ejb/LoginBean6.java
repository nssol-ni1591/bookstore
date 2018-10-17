
package bookstore.jsf.bean.ejb;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import bookstore.jsf.bean.AbstractLoginBean;
import bookstore.service.CustomerService;

@Named
@RequestScoped
public class LoginBean6 extends AbstractLoginBean {

	@EJB(mappedName="CustomerServiceEjbBmtWrapper") private CustomerService customerService;

	public LoginBean6() {
		super();
	}

	@Override
	protected CustomerService getCustomerService() {
		return customerService;
	}

	protected String getLoginPage() {
		return "Login6";
	}
	protected String getBookStorePage() {
		return "BookStore6";
	}

}
