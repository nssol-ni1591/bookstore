package bookstore.dao.jdbctemplate;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import bookstore.annotation.Log;
import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.dao.jdbc.CustomerDAOImpl;
import bookstore.dao.jdbc.DB;
import bookstore.pbean.TCustomer;
import bookstore.pbean.TOrder;

@Repository("OrderDAOImplBId2")
public class OrderDAOImpl<T extends JdbcTemplate> implements OrderDAO<T> {

	@Log private static Logger log;

	public TOrder createOrder(final T jdbcTemplate, TCustomer inCustomer) throws SQLException {
		log.log(Level.INFO, "jdbcTemplate={0}", jdbcTemplate);

		LocalPreparedStatementCreator psc = new LocalPreparedStatementCreator() {
			private PreparedStatement pst;

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				pst = con.prepareStatement(
						"insert into T_Order (customer_id_fk, orderday) values (?,?)"
						, Statement.RETURN_GENERATED_KEYS);
				pst.setInt(1, inCustomer.getId());
				pst.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
				return pst;
			}
			public PreparedStatement getPreparedStatement() {
				return pst;
			}
		};

		log.log(Level.INFO, "execute psc={0}, customer_id={1}", new Object[] { psc, inCustomer.getId() });
		final KeyHolder holder = new GeneratedKeyHolder();
		if (jdbcTemplate.update(psc, holder) <= 0) {
			log.log(Level.SEVERE, "failed psc={0}", psc);
			throw new SQLException("failed insert");
		}
		
		TOrder order = new TOrder();
		order.setTCustomer(inCustomer);
		order.setOrderday(Timestamp.valueOf(LocalDateTime.now()));
		order.setId(holder.getKey().intValue());

		log.log(Level.INFO, "customer_id={0}, order_id={1}"
				, new Object[] { order.getTCustomer().getId(), order.getId() });
		return order;
	}

	public List<TOrder> retrieveOrders(final T jdbcTemplate, final List<String> orderIdList) {
		String sql;
		if (orderIdList == null || orderIdList.isEmpty()) {
			sql = "select id, custormerId, orderDay from T_Order";
		}
		else {
			sql = "select id, custormerId, orderDay from T_Order where id in ('"
					+ String.join("','", orderIdList.toArray(new String[0])) + "')";
		}

		return jdbcTemplate.query(sql, new RowMapper<TOrder>() {

			@Override
			public TOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
				int id = rs.getInt(1);
				String customerId = rs.getString(2);
				Timestamp orderDay = rs.getTimestamp(3);

				CustomerDAO<Connection> customerDAO = new CustomerDAOImpl<>();
				//TCustomer customer = customerDAO.findCustomerByUid(con, custormerId)
				TCustomer customer = customerDAO.findCustomerByUid(DB.createConnection(), customerId);

				TOrder order = new TOrder();
				order.setId(id);
				order.setTCustomer(customer);
				order.setOrderday(orderDay);
				return order;
			}
		});
	}

	interface LocalPreparedStatementCreator extends PreparedStatementCreator {
		public PreparedStatement getPreparedStatement();
	}

}
