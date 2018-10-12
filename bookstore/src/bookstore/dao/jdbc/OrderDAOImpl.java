package bookstore.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.pbean.TCustomer;
import bookstore.pbean.TOrder;

public class OrderDAOImpl<T extends Connection> implements OrderDAO<T> {

	private static final Logger log = Logger.getLogger(OrderDAOImpl.class.getName());

	public TOrder createOrder(final T con2, TCustomer customer) throws SQLException {
		Connection con = con2 != null ? con2 : DB.createConnection();
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		ResultSet rs2 = null;

		try {
			Timestamp now = Timestamp.valueOf(LocalDateTime.now());
			pst = con.prepareStatement("insert into T_Order (customer_id_fk, orderday) values (?,?)"
					, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, customer.getId());
			pst.setTimestamp(2, now);
			if (pst.executeUpdate() <= 0) {
				log.log(Level.SEVERE, "failed sql: {0}", pst);
				throw new SQLException("failed insert");
			}

			/* �����̔Ԃ��ꂽ�l���擾���� */
			rs2 = pst.getGeneratedKeys();
			/*
			pst2 = con.prepareStatement("select max(id)"
					+ " from t_order"
					+ " where customer_id_fk = ?"
					)
			pst2.setInt(1, inCustomer.getId())
			rs2 = pst2.executeQuery()
			*/
			if (rs2.next()) {
				TOrder saveOrder = new TOrder();
				saveOrder.setTCustomer(customer);
				saveOrder.setOrderday(now);
				saveOrder.setId(rs2.getInt(1));

				if (con2 == null) {
					con.commit();
				}
				log.log(Level.INFO, "customer_id={0}, order_id={1}"
						, new Object[] { customer.getId(), saveOrder.getId() });
				return saveOrder;
			}
			else {
				log.log(Level.SEVERE, "failed get id: {0}", pst2);
			}
			if (con2 == null) {
				con.rollback();
			}
		}
		catch (SQLException e) {
			log.log(Level.SEVERE, "", e);
			if (con2 == null) {
				con.rollback();
			}
		}
		finally {
			DB.close(rs2, pst2, null);
			DB.close(null, pst, con2 != null ? null : con);
		}
		return null;
	}

	public List<TOrder> retrieveOrders(final T con2, final List<String> orderIdList) throws SQLException {
		Connection con = con2 != null ? con2 : DB.createConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<TOrder> orderList = new ArrayList<>();
		try {
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
				String customerId = rs.getString(2);
				Timestamp orderDay = rs.getTimestamp(3);

				CustomerDAO<Connection> customerDAO = new CustomerDAOImpl<>();
				TCustomer customer = customerDAO.findCustomerByUid(con, customerId);

				TOrder o = new TOrder();
				o.setId(id);
				o.setTCustomer(customer);
				o.setOrderday(orderDay);
				orderList.add(o);
			}
		}
		finally {
			DB.close(rs, pst, con2 != null ? null : con);
		}
		return orderList;
	}
}
