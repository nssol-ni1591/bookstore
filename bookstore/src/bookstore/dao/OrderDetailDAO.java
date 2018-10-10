package bookstore.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookstore.pbean.TBook;
import bookstore.pbean.TOrder;
import bookstore.pbean.TOrderDetail;

public interface OrderDetailDAO<T> {

	void createOrderDetail(final T em, TOrder order, TBook book) throws SQLException;

	default List<TOrderDetail> listOrderDetails(final T em, List<String> orders) throws SQLException {
		return new ArrayList<>();
	}

}
