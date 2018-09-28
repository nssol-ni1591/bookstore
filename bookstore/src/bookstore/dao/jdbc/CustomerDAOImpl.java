package bookstore.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import bookstore.dao.CustomerDAO;
import bookstore.pbean.TCustomer;

public class CustomerDAOImpl<T extends Connection> implements CustomerDAO<T> {

	private static final Logger log = Logger.getLogger(CustomerDAOImpl.class.getName());

	public int getCustomerNumberByUid(final T con2, final String inUid) throws SQLException {
		Connection con = con2 != null ? con2 : DB.createConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = con.prepareStatement("select count(*) from T_Customer where username = ?");
			pst.setString(1, inUid);
			rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		}
		finally {
			DB.close(OrderDAOImpl.class.getName(), rs, pst, con2 != null ? null : con);
		}
		return 0;
	}

	public TCustomer findCustomerByUid(final T con2, final String inUid) throws SQLException {
		Connection con = con2 != null ? con2 : DB.createConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = con.prepareStatement("select id, username, passwordmd5, name, email from T_Customer where username = ?");
			pst.setString(1, inUid);
			rs = pst.executeQuery();
			if (rs.next()) {
				int id = rs.getInt(1);
				String username = rs.getString(2);
				String passwordmd5 = rs.getString(3);
				String name = rs.getString(4);
				String email = rs.getString(5);

				TCustomer c = new TCustomer();
				c.setId(id);
				c.setUsername(username);
				c.setPasswordmd5(passwordmd5);
				c.setName(name);
				c.setEmail(email);
				return c;
			}
		}
		finally {
			DB.close(OrderDAOImpl.class.getName(), rs, pst, con2 != null ? null : con);
		}
		return null;
	}

	public void saveCustomer(final T con2, 
			String inUid,
			String inPasswordMD5,
			String inName,
			String inEmail) throws SQLException {
		Connection con = con2 != null ? con2 : DB.createConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("insert into T_Customer (username, passwordmd5, name, email) values (?,?,?,?)");
			pst.setString(1, inUid);
			pst.setString(2, inPasswordMD5);
			pst.setString(3, inName);
			pst.setString(4, inEmail);
			if (pst.executeUpdate() <= 0) {
				log.log(Level.SEVERE, "failed sql: {0}", pst);
			}
		}
		finally {
			DB.close(OrderDAOImpl.class.getName(), null, pst, con2 != null ? null : con);
		}
	}
}
