package bookstore.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import bookstore.dao.OrderDetailDAO;
import bookstore.pbean.TBook;
import bookstore.pbean.TOrder;

public class OrderDetailDAOImpl<T extends Connection> implements OrderDetailDAO<T> {

	private static final Logger log = Logger.getLogger(OrderDetailDAOImpl.class.getName());

	public void createOrderDetail(final T con2, TOrder order, TBook book) throws SQLException {
		log.log(Level.INFO, "order_id={0}, book_id={1}"
				, new Object[] { order.getId(), book.getId() });

		if ("0-0000-0000-0".equals(book.getIsbn())) {
			//throw new SQLException("isdn: 0-0000-0000-0");
			order.setId(0);
		}

		Connection con = con2 != null ? con2 : DB.createConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("insert into T_Order_Detail (order_id_fk, book_id_fk) values (?,?)");
			pst.setInt(1, order.getId());
			pst.setInt(2, book.getId());
			log.log(Level.INFO, "execute sql: {0}, order_id={1}, book_id={2}"
					, new Object[] { pst, order.getId(), book.getId() });
			if (pst.executeUpdate() <= 0) {
				log.log(Level.SEVERE, "failed sql: {0}", pst);
				if (con2 == null) {
					con.commit();
				}
			}
			else {
				if (con2 == null) {
					con.rollback();
				}
			}
		}
		finally {
			DB.close(null, pst, con2 != null ? null : con);
		}
	}

}
