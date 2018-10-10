package bookstore.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookstore.vbean.VOrder;
import bookstore.vbean.VOrderDetail;

public interface OrderService {

	public void orderBooks(String uid, List<String> isbnList) throws Exception;

	// add by gohdo.
	public List<VOrder> listOrders(List<String> orderIdList) throws SQLException;

	default List<VOrderDetail> listOrderDetails(List<String> orders) throws SQLException {
		return new ArrayList<>();
	}

}
