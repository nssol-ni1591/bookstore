package bookstore.dao.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bookstore.dao.CustomerDAO;
import bookstore.pbean.TCustomer;

public class CustomerDAOImpl implements CustomerDAO {

	public int getCustomerNumberByUid(final String inUid) {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = DB.createConnection ();
			pst = con.prepareStatement("select count(*) from T_Customer where username = ?");
			pst.setString(1, inUid);
			rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		}
		catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (rs != null ) {
				try {
					rs.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
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
		return 0;
	}

	public TCustomer findCustomerByUid(final String inUid) {

		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = DB.createConnection ();
			pst = con.prepareStatement("select id, username, passwordmd5, name, email from T_Customer where username = ?");
			pst.setString(1, inUid);
			rs = pst.executeQuery();
			if (rs.next()) {
				int id = rs.getInt(1);
				String username = rs.getString(2);
				String passwordmd5 = rs.getString(3);
				String name = rs.getString(4);
				String email = rs.getString(5);
				return new TCustomer(id, username, passwordmd5, name, email);
			}
		}
		catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (rs != null ) {
				try {
					rs.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
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
		return null;
	}

	public void saveCustomer(String inUid,
			String inPasswordMD5,
			String inName,
			String inEmail) {

		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = DB.createConnection ();
			pst = con.prepareStatement("insert into T_Customer (username, passwordmd5, name, email) values (?,?,?,?)");
			pst.setString(1, inUid);
			pst.setString(2, inPasswordMD5);
			pst.setString(3, inName);
			pst.setString(4, inEmail);
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