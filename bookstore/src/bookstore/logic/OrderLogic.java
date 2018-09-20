package bookstore.logic;

import java.sql.SQLException;
import java.util.List;

import bookstore.vbean.VOrder;
import bookstore.vbean.VOrderDetail;

public interface OrderLogic {

	public void orderBooks(String inUid, List<String> inISBNs) throws SQLException;

	// add by gohdo.
	public List<VOrder> listOrders(List<String> orderIdList) throws SQLException;

	default List<VOrderDetail> listOrderDetails(List<String> orders) throws SQLException {
		return null;
	}

}
