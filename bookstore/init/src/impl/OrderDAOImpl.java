package bookstore.dao.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
		ResultSet rs2 = null;
		try {
			con = DB.createConnection();
			pst = con.prepareStatement("insert into T_Order (customer_id_fk, orderday) values (?,?)");
			pst.setInt(1, inCustomer.getId());
			pst.setDate(2, new java.sql.Date(saveOrder.getOrderday().getTime()));
			if (!pst.execute()) {
				Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, "failed sql: {0}", pst);
				return null;
			}
			pst2 = con.prepareStatement("select AUTOINCREMENTVALUE, COLUMNNAME, AUTOINCREMENTSTART, AUTOINCREMENTINC"
					+ " from sys.systables t, sys.syscolumns c" + " where t.tablename='T_ORDER'"
					+ "  and c.referenceid=t.tableid" + "  and c.columnname='ID");
			rs2 = pst2.executeQuery();
			if (rs2.next()) {
				saveOrder.setId(rs2.getInt(1));
			}
			else {
				Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, "failed get id: {0}", pst2);
			}
		}
		catch (ClassNotFoundException | IOException | SQLException e) {
			Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, "", e);
		}
		finally {
			DB.close(OrderDAOImpl.class.getName(), rs, pst, con);
			DB.close(OrderDAOImpl.class.getName(), rs2, pst2, null);
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
			Logger.getLogger(OrderDAOImpl.class.getName()).log(Level.SEVERE, "", e);
		}
		finally {
			DB.close(OrderDAOImpl.class.getName(), rs, pst, con);
		}
		return orderList;
	}
}
