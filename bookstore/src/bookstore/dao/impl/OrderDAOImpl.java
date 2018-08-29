package bookstore.dao.impl;

import java.util.List;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.pbean.TCustomer;
import bookstore.pbean.TOrder;

public class OrderDAOImpl implements OrderDAO {

	public TOrder createOrder(TCustomer inCustomer) {

		TOrder saveOrder = new TOrder();
		saveOrder.setTCustomer(inCustomer);
		saveOrder.setOrderday(Calendar.getInstance().getTime());

		Connection con = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		ResultSet rs = null;
		try {
			con = DB.createConnection();
			pst = con.prepareStatement("insert into T_Order (customer_id_fk, orderday) values (?,?)");
			pst.setInt(1, inCustomer.getId());
			pst.setDate(2, new java.sql.Date(saveOrder.getOrderday().getTime()));
			if (!pst.execute()) {
				System.err.println("failed sql: \"" + pst.toString() + "\"");
				return null;
			}
			pst2 = con.prepareStatement("select AUTOINCREMENTVALUE, COLUMNNAME, AUTOINCREMENTSTART, AUTOINCREMENTINC"
					+ " from sys.systables t, sys.syscolumns c" + " where t.tablename='T_ORDER'"
					+ "  and c.referenceid=t.tableid" + "  and c.columnname='ID");
			rs = pst2.executeQuery();
			if (rs.next()) {
				saveOrder.setId(rs.getInt(1));
			}
			else {
				System.err.println("failed get id: \"" + pst2.toString() + "\"");
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
			if (pst2 != null) {
				try {
					pst2.close();
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
		return saveOrder;
	}

	public List<TOrder> retrieveOrders(final List<String> orderIdList) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<TOrder> orderList = new ArrayList<>();
		try {
			con = DB.createConnection();
			if (orderIdList == null || orderIdList.isEmpty()) {
				pst = con.prepareStatement("select id, custormerId, orderDay from T_Order");
			}
			else {
				pst = con.prepareStatement("select id, custormerId, orderDay from T_Order where id in ('"
						+ String.join("','", orderIdList.toArray(new String[0])) + "')");
			}
			rs = pst.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String custormerId = rs.getString(2);
				Date orderDay = rs.getDate(3);

				CustomerDAO customerDAO = new CustomerDAOImpl();
				TCustomer custormer = customerDAO.findCustomerByUid(custormerId);

				orderList.add(new TOrder(id, custormer, orderDay));
			}
		}
		catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (rs != null) {
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
		return orderList;
	}
}
