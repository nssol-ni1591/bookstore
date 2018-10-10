package bookstore.jsf.bean.ejbeclipselink;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import bookstore.jsf.bean.AbstractLoginBean;
import bookstore.service.CustomerService;

@Named
@RequestScoped
public class LoginBean5 extends AbstractLoginBean {

	@EJB(mappedName="CustomerServiceEjbWrapper") private CustomerService customerService;

	public LoginBean5() {
		super();
	}

	@Override
	protected CustomerService getCustomerService() {
		return customerService;
	}

	protected String getLoginPage() {
		return "Login5";
	}
	protected String getBookStorePage() {
		return "BookStore5";
	}

}
