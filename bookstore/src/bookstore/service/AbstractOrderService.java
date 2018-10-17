package bookstore.service;

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

public abstract class AbstractOrderService<T> implements OrderService {

	protected abstract BookDAO<T> getBookDAO();
	protected abstract CustomerDAO<T> getCustomerDAO();
	protected abstract OrderDAO<T> getOrderDAO();
	protected abstract OrderDetailDAO<T> getOrderDetailDAO();
	protected abstract Logger getLogger();
	protected abstract T getManager();


	@Override
	public void orderBooks(String uid, List<String> isbnList) throws SQLException {
		Logger log = getLogger();
		T em = getManager();
		BookDAO<T> bookdao = getBookDAO();
		CustomerDAO<T> customerdao = getCustomerDAO();
		OrderDAO<T> orderdao = getOrderDAO();
		OrderDetailDAO<T> odetaildao = getOrderDetailDAO();

		TCustomer customer = customerdao.findCustomerByUid(em, uid);
		TOrder order = orderdao.createOrder(em, customer);
		log.log(Level.INFO, "uid={0}, customer_id={1}, order_id={2}"
				, new Object[] { uid, customer.getId(), order.getId() });

		Iterator<String> iter = isbnList.iterator();
		while (iter.hasNext()) {
			String isbn = iter.next();
			List<String> isbns = new ArrayList<>();
			isbns.add(isbn);
			List<TBook> books = bookdao.retrieveBooksByISBNs(em, isbns);
			TBook book = books.get(0);
			odetaildao.createOrderDetail(em, order, book);
		}
	}

	@Override
	public List<VOrder> listOrders(List<String> orderIdList) throws SQLException {
		List<VOrder> orderList = new ArrayList<>();

		T em = getManager();
		OrderDAO<T> orderdao = getOrderDAO();

		Iterator<TOrder> iter = orderdao.retrieveOrders(em, orderIdList).iterator();
		while (iter.hasNext()) {
			TOrder currentOrder = iter.next();
			VOrder currentVOrder = new VOrder(currentOrder);
			orderList.add(currentVOrder);
		}
		return orderList;
	}

	@Override
	public List<VOrderDetail> listOrderDetails(List<String> orders) throws SQLException {
		List<VOrderDetail> list = new ArrayList<>();

		T em = getManager();
		OrderDetailDAO<T> odetaildao = getOrderDetailDAO();
		Iterator<TOrderDetail> iter = odetaildao.listOrderDetails(em, orders).iterator();

		while (iter.hasNext()) {
			TOrderDetail currentOrder = iter.next();
			VOrderDetail currentVOrder = new VOrderDetail(currentOrder);
			list.add(currentVOrder);
		}
		return list;
	}

}
