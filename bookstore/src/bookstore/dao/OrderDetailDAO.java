package bookstore.dao;

import java.sql.SQLException;
import java.util.List;

import bookstore.pbean.TBook;
import bookstore.pbean.TOrder;
import bookstore.pbean.TOrderDetail;

public interface OrderDetailDAO {

	TOrderDetail createOrderDetail(TOrder inOrder, TBook inBook) throws SQLException;

	default List<TOrderDetail> listOrderDetails(List<String> orders) throws SQLException {
		return null;
	}

}
