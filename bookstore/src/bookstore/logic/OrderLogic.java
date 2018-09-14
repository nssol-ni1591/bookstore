package bookstore.logic;

import java.util.List;

import bookstore.vbean.VOrder;
import bookstore.vbean.VOrderDetail;

public interface OrderLogic {

	public void orderBooks(String inUid, List<String> inISBNs);

	// add by gohdo.
	public List<VOrder> listOrders(List<String> orderIdList);
	default List<VOrderDetail> listOrderDetails(List<String> orders) {
		return null;
	}

}
