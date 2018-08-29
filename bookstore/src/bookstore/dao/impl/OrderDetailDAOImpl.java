package bookstore.dao.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bookstore.dao.OrderDetailDAO;
import bookstore.pbean.TBook;
import bookstore.pbean.TOrder;

public class OrderDetailDAOImpl implements OrderDetailDAO {

	public void createOrderDetail(TOrder inOrder, TBook inBook) {

		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = DB.createConnection();
			pst = con.prepareStatement("insert into T_Order (order_id_fk, book_id_fk) values (?,?)");
			pst.setInt(1, inOrder.getId());
			pst.setInt(2, inBook.getId());
			if (!pst.execute()) {
				System.err.println("failed sql: \"" + pst.toString() + "\"");
			}
		}
		catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (pst != null) {
				try {
					pst.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}