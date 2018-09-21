package bookstore.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import bookstore.dao.OrderDetailDAO;
import bookstore.pbean.TBook;
import bookstore.pbean.TOrder;
import bookstore.pbean.TOrderDetail;

public class OrderDetailDAOImpl<T extends Connection> implements OrderDetailDAO<T> {

	private static final Logger log = Logger.getLogger(OrderDetailDAOImpl.class.getName());

	public TOrderDetail createOrderDetail(final T con, TOrder inOrder, TBook inBook) throws SQLException {
		log.log(Level.INFO, "order_id={0}, book_id={1}"
				, new Object[] { inOrder.getId(), inBook.getId() });

		if ("0-0000-0000-0".equals(inBook.getIsbn())) {
			throw new SQLException("isdn: 0-0000-0000-0");
		}

		//try {
			//Connection con = null;
			//con = DB.createConnection();

			PreparedStatement pst = null;
			TOrderDetail orderDetail = null;
			try {
				pst = con.prepareStatement("insert into T_Order_Detail (order_id_fk, book_id_fk) values (?,?)");
				pst.setInt(1, inOrder.getId());
				pst.setInt(2, inBook.getId());
				log.log(Level.INFO, "execute sql: {0}, order_id={1}, book_id={2}"
						, new Object[] { pst, inOrder.getId(), inBook.getId() });
				if (pst.executeUpdate() <= 0) {
					log.log(Level.SEVERE, "failed sql: {0}", pst);
				}
				else {
					//con.commit();
					orderDetail = new TOrderDetail();
					orderDetail.setTOrder(inOrder);
					orderDetail.setTBook(inBook);
					return orderDetail;
				}
				//con.rollback();
			}
//			catch (SQLException e) {
//				log.log(Level.SEVERE, "", e);
//			}
			finally {
				DB.close(OrderDetailDAOImpl.class.getName(), null, pst, null);
			}
//		}
//		catch (ClassNotFoundException | IOException | NamingException e) {
//			throw new SQLException(e);
//		}
		return null;
	}

}