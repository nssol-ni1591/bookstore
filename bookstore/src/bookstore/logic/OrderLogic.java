package bookstore.logic;

import java.util.List;

public interface OrderLogic {
	public void orderBooks( String inUid, List inISBNs );
  // add by gohdo.
	public List listOrders( List orderIdList );
}
