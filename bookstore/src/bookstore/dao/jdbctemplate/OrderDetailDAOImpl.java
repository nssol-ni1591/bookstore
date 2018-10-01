package bookstore.dao.jdbctemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import bookstore.dao.OrderDetailDAO;
import bookstore.pbean.TBook;
import bookstore.pbean.TOrder;

public class OrderDetailDAOImpl<T extends JdbcTemplate> implements OrderDetailDAO<T> {

	private static final Logger log = Logger.getLogger(OrderDetailDAOImpl.class.getName());

	public void createOrderDetail(final T jdbcTemplate, TOrder inOrder, TBook inBook) throws SQLException {
		log.log(Level.INFO, "order_id={0}, book_id={1}"
				, new Object[] { inOrder.getId(), inBook.getId() });

		if ("0-0000-0000-0".equals(inBook.getIsbn())) {
			throw new SQLException("isdn: 0-0000-0000-0");
		}

		PreparedStatementCreator psc = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pst = con.prepareStatement(
						"insert into T_Order_Detail (order_id_fk, book_id_fk) values (?,?)"
						, Statement.RETURN_GENERATED_KEYS);
				pst.setInt(1, inOrder.getId());
				pst.setInt(2, inBook.getId());
				log.log(Level.INFO, "execute sql: {0}, order_id={1}, book_id={2}"
						, new Object[] { pst, inOrder.getId(), inBook.getId() });
				return pst;
			}
		};

		final KeyHolder holder = new GeneratedKeyHolder();
		if (jdbcTemplate.update(psc, holder) <= 0) {
			log.log(Level.SEVERE, "failed psc={0}", psc);
			throw new SQLException("failed insert");
		}
		log.log(Level.INFO, "detail_id={0}, order_id={1}, book_id={2}"
				, new Object[] { holder.getKey().intValue(), inOrder.getId(), inBook.getId() });
	}

}
