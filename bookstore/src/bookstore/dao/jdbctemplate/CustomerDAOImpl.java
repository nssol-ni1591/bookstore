package bookstore.dao.jdbctemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;

import bookstore.annotation.Log;
import bookstore.dao.CustomerDAO;
import bookstore.pbean.TCustomer;

public class CustomerDAOImpl<T extends JdbcTemplate> implements CustomerDAO<T> {

	//JdbcTemplateの場合、Tx内ではJdbcTemplateのインスタンスを引き回すすこと
	//よって、以下のjdbcTemplateを使用するロジックはNG
	@Autowired JdbcTemplate jdbcTemplate3;
	@Log private static Logger log;

	public int getCustomerNumberByUid(final T jdbcTemplate2, final String inUid) throws SQLException {
		JdbcTemplate jdbcTemplate = jdbcTemplate2 != null ? jdbcTemplate2 : jdbcTemplate3;
		return jdbcTemplate.queryForObject("select count(*) from T_Customer where username = ?"
				, new Object[] { inUid }
				, Integer.class);
	}

	public TCustomer findCustomerByUid(final T jdbcTemplate2, final String inUid) throws SQLException {
		JdbcTemplate jdbcTemplate = jdbcTemplate2 != null ? jdbcTemplate2 : jdbcTemplate3;
		return jdbcTemplate.query(
				new PreparedStatementCreator() {

					@Override
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
						PreparedStatement pst = con.prepareStatement(
								"select id, username, passwordmd5, name, email from T_Customer where username = ?");
						pst.setString(1, inUid);
						return pst;
					}
				}
				, new ResultSetExtractor<TCustomer>() {

					@Override
					public TCustomer extractData(ResultSet rs) throws SQLException {
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
						return null;
					}
				});
	}

	public void saveCustomer(final T jdbcTemplate2, 
			String inUid,
			String inPasswordMD5,
			String inName,
			String inEmail) throws SQLException {
		JdbcTemplate jdbcTemplate = jdbcTemplate2 != null ? jdbcTemplate2 : jdbcTemplate3;
		PreparedStatementCreator psc = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pst = con.prepareStatement(
						"insert into T_Customer (username, passwordmd5, name, email) values (?,?,?,?)");
				pst.setString(1, inUid);
				pst.setString(2, inPasswordMD5);
				pst.setString(3, inName);
				pst.setString(4, inEmail);
				return pst;
			}
		};

		log.log(Level.INFO, "execute sql: {0}, uid={1}", new Object[] { psc, inUid });
		if (jdbcTemplate.update(psc) <= 0) {
			log.log(Level.SEVERE, "failed psc={0}", psc);
			throw new SQLException("failed insert");
		}
	}

}
