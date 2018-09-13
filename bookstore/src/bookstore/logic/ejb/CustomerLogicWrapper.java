package bookstore.logic.ejb;

import bookstore.annotation.UsedOpenJpa;
import bookstore.dao.CustomerDAO;
import bookstore.logic.impl.CustomerLogicImpl;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
@LocalBean
public class CustomerLogicWrapper extends CustomerLogicImpl {

	@Inject @UsedOpenJpa CustomerDAO customerdao;
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
