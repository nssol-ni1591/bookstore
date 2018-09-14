package bookstore.dao;

import java.util.List;

import bookstore.pbean.TBook;
import bookstore.pbean.TOrder;
import bookstore.pbean.TOrderDetail;

public interface OrderDetailDAO {

	TOrderDetail createOrderDetail(TOrder inOrder, TBook inBook);

	default List<TOrderDetail> listOrderDetails(List<String> orders) {
		return null;
	}

}
