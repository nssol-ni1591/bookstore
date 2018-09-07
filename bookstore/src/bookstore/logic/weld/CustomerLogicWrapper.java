package bookstore.logic.weld;

import java.util.logging.Logger;

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
	@Inject Logger log;

	@PostConstruct
	public void init() {
		super.setCustomerdao(customerdao);
	}
	
	@Override
	protected Logger getLogger() {
		return log;
	}

}
