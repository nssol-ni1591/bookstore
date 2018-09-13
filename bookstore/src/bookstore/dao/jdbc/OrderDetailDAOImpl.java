package bookstore.dao.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import bookstore.dao.OrderDetailDAO;
import bookstore.pbean.TBook;
import bookstore.pbean.TOrder;

public class OrderDetailDAOImpl implements OrderDetailDAO {

	private static final Logger log = Logger.getLogger(OrderDetailDAOImpl.class.getName());

	public void createOrderDetail(TOrder inOrder, TBook inBook) {

		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = DB.createConnection();
			pst = con.prepareStatement("insert into T_Order (order_id_fk, book_id_fk) values (?,?)");
			pst.setInt(1, inOrder.getId());
			pst.setInt(2, inBook.getId());
			if (!pst.execute()) {
				log.log(Level.SEVERE, "failed sql: {0}", pst);
			}
		}
		catch (ClassNotFoundException | IOException | SQLException e) {
			log.log(Level.SEVERE, "", e);
		}
		finally {
			DB.close(OrderDetailDAOImpl.class.getName(), null, pst, con);
		}
	}

}