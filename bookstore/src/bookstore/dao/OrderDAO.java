package bookstore.dao;

import bookstore.pbean.TCustomer;
import bookstore.pbean.TOrder;

import java.sql.SQLException;
import java.util.List;

public interface OrderDAO {

	public TOrder createOrder(TCustomer inCustomer) throws SQLException;

	// add by gohdo
	public List<TOrder> retrieveOrders(List<String> orderIdList) throws SQLException;

}
