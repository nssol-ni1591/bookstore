package bookstore.logic.weld;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import bookstore.annotation.UsedWeld;
import bookstore.annotation.UsedEclipselink;
import bookstore.dao.BookDAO;
import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.dao.OrderDetailDAO;
import bookstore.logic.AbstractOrderLogic;
import bookstore.pbean.TBook;
import bookstore.pbean.TCustomer;
import bookstore.pbean.TOrder;

@UsedWeld
@Dependent
public class OrderLogicWrapper extends AbstractOrderLogic {

	@Inject @UsedEclipselink private BookDAO bookdao;
	@Inject @UsedEclipselink private CustomerDAO customerdao;
	@Inject @UsedEclipselink private OrderDAO orderdao;
	@Inject @UsedEclipselink private OrderDetailDAO orderdetaildao;
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
		log.log(Level.INFO, "uid={0}, isbn={1}", new Object[] { inUid, inISBNs });

		// Exception Description: Cannot use an EntityTransaction while using JTA
		//em.getTransaction().begin();

		// WHY?:
		// Caused by: java.lang.Error: Unresolved compilation problem: 
		// The field OrderLogicImpl.odetaildao is not visible
		//super.orderBooks(inUid, inISBNs);
		
		TCustomer customer = customerdao.findCustomerByUid(inUid);
		TOrder order = orderdao.createOrder(customer);

		Iterator<TBook> iter = bookdao.retrieveBooksByISBNs(inISBNs).iterator();
		while (iter.hasNext()) {
			TBook book = iter.next();
			orderdetaildao.createOrderDetail(order, book);
		}
		// Exception Description: Cannot use an EntityTransaction while using JTA
		//em.getTransaction().commit();
	}

}
