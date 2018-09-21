package bookstore.dao;

import bookstore.pbean.TCustomer;
import bookstore.pbean.TOrder;

import java.sql.SQLException;
import java.util.List;

public interface OrderDAO<T> {

	public TOrder createOrder(final T em, final TCustomer inCustomer) throws SQLException;

	// add by gohdo
	public List<TOrder> retrieveOrders(final T em, final List<String> orderIdList) throws SQLException;

}
