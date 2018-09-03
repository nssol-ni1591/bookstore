package bookstore.logic;

import java.util.List;

import bookstore.vbean.VOrder;

public interface OrderLogic {

	public void orderBooks(String inUid, List<String> inISBNs);

	// add by gohdo.
	public List<VOrder> listOrders(List<String> orderIdList);

}
