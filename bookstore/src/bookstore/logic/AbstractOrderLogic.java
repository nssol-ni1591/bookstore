package bookstore.logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import bookstore.dao.BookDAO;
import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.dao.OrderDetailDAO;
import bookstore.pbean.TBook;
import bookstore.pbean.TCustomer;
import bookstore.pbean.TOrder;
import bookstore.pbean.TOrderDetail;
import bookstore.vbean.VOrder;
import bookstore.vbean.VOrderDetail;

public abstract class AbstractOrderLogic implements OrderLogic {

	protected abstract BookDAO getBookDAO();
	protected abstract CustomerDAO getCustomerDAO();
	protected abstract OrderDAO getOrderDAO();
	protected abstract OrderDetailDAO getOrderDetailDAO();
	protected abstract Logger getLogger();

	public void orderBooks(String inUid, List<String> inISBNs) throws SQLException {
		Logger log = getLogger();
		BookDAO bookdao = getBookDAO();
		CustomerDAO customerdao = getCustomerDAO();
		OrderDAO orderdao = getOrderDAO();
		OrderDetailDAO odetaildao = getOrderDetailDAO();

		TCustomer customer = customerdao.findCustomerByUid(inUid);
		TOrder order = orderdao.createOrder(customer);
		log.log(Level.INFO, "uid={0}, customer_id={1}, order_id={2}"
				, new Object[] { inUid, customer.getId(), order.getId() });

		Iterator<TBook> iter = bookdao.retrieveBooksByISBNs(inISBNs).iterator();
		while (iter.hasNext()) {
			TBook book = iter.next();
			odetaildao.createOrderDetail(order, book);
		}
	}

	public List<VOrder> listOrders(List<String> orderIdList) throws SQLException {
		List<VOrder> orderList = new ArrayList<>();

		OrderDAO orderdao = getOrderDAO();
		Iterator<TOrder> iter = orderdao.retrieveOrders(orderIdList).iterator();

		while (iter.hasNext()) {
			TOrder currentOrder = iter.next();
			VOrder currentVOrder = new VOrder(currentOrder);
			orderList.add(currentVOrder);
		}
		return orderList;
	}

	public List<VOrderDetail> listOrderDetails(List<String> orders) throws SQLException {
		List<VOrderDetail> list = new ArrayList<>();

		OrderDetailDAO odetaildao = getOrderDetailDAO();
		Iterator<TOrderDetail> iter = odetaildao.listOrderDetails(orders).iterator();

		while (iter.hasNext()) {
			TOrderDetail currentOrder = iter.next();
			VOrderDetail currentVOrder = new VOrderDetail(currentOrder);
			list.add(currentVOrder);
		}
		return list;
	}

}
