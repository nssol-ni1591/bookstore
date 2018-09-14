package bookstore.dao;

import bookstore.pbean.TBook;
import bookstore.pbean.TOrder;

public interface OrderDetailDAO {

	public void createOrderDetail(TOrder inOrder, TBook inBook);

}
