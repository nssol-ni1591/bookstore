package bookstore.dao;

import java.sql.SQLException;
import java.util.List;

import bookstore.pbean.TBook;
import bookstore.pbean.TOrder;
import bookstore.pbean.TOrderDetail;

public interface OrderDetailDAO<T> {

	void createOrderDetail(final T em, TOrder inOrder, TBook inBook) throws SQLException;

	default List<TOrderDetail> listOrderDetails(final T em, List<String> orders) throws SQLException {
		return null;
	}

}
