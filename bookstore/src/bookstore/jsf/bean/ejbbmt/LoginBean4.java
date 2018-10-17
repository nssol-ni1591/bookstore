package bookstore.jsf.bean.ejbbmt;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import bookstore.annotation.UsedBMT;
import bookstore.jsf.bean.AbstractLoginBean;
import bookstore.service.CustomerService;
import bookstore.service.ejb.CustomerServiceRemote;

@Named
@RequestScoped
public class LoginBean4 extends AbstractLoginBean {

	// @Remote
	//@EJB(mappedName="CustomerServiceBmtWrapper") private CustomerServiceRemote customerService;
	@EJB @UsedBMT private CustomerServiceRemote customerService;


	@Override
	protected CustomerService getCustomerService() {
		return customerService;
	}

	@Override
	protected String getLoginPage() {
		return "Login4";
	}
	@Override
	protected String getBookStorePage() {
		return "BookStore4";
	}

}
