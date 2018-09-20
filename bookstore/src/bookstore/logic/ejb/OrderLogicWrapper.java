package bookstore.logic.ejb;

import bookstore.annotation.UsedOpenJpa;
import bookstore.dao.BookDAO;
import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.dao.OrderDetailDAO;
import bookstore.logic.OrderLogic;
import bookstore.logic.AbstractOrderLogic;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

@Stateless
@LocalBean
@Local(OrderLogic.class)
public class OrderLogicWrapper extends AbstractOrderLogic {

	@Inject @UsedOpenJpa private BookDAO bookdao;
	@Inject @UsedOpenJpa private CustomerDAO customerdao;
	@Inject @UsedOpenJpa private OrderDAO orderdao;
	@Inject @UsedOpenJpa private OrderDetailDAO orderdetaildao;
	@Inject private Logger log;

	@Override
	protected BookDAO getBookDAO() {
		return bookdao;
	}
	@Override
	protected CustomerDAO getCustomerDAO() {
		return customerdao;
	}
	@Override
	protected OrderDAO getOrderDAO() {
		return orderdao;
	}
	@Override
	protected OrderDetailDAO getOrderDetailDAO() {
		return orderdetaildao;
	}
	@Override
	protected Logger getLogger() {
		return log;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void orderBooks(String inUid, List<String> inISBNs) throws SQLException {
		super.orderBooks(inUid, inISBNs);
	}

}
