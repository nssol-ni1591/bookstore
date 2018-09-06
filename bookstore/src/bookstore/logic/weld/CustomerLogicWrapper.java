package bookstore.logic.weld;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import bookstore.annotation.UsedWeld;
import bookstore.annotation.UsedEclipselink;
import bookstore.dao.CustomerDAO;
import bookstore.logic.impl.CustomerLogicImpl;

@Named
@UsedWeld
public class CustomerLogicWrapper extends CustomerLogicImpl {

	@Inject @UsedEclipselink CustomerDAO customerdao;
	//@ManagedProperty @UsedEclipselink CustomerDAO customerdao;

	@PostConstruct
	public void init() {
		System.out.println("weld.CustomerLogicWrapper.init: customerdao=" + customerdao);
		super.setCustomerdao(customerdao);
	}

}
