package bookstore.dao.jdbctemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import bookstore.dao.BookDAO;
import bookstore.pbean.TBook;

public class BookDAOImpl<T extends JdbcTemplate> implements BookDAO<T> {

	//JdbcTemplateの場合、Tx内ではJdbcTemplateのインスタンスを引き回すすこと
	//よって、以下のjdbcTemplateを使用するロジックはNG
	@Autowired JdbcTemplate jdbcTemplate3;

	@Override
	public int getPriceByISBNs(final T jdbcTemplate2, final List<String> inISBNList) throws SQLException {
		JdbcTemplate jdbcTemplate = jdbcTemplate2 != null ? jdbcTemplate2 : jdbcTemplate3;
		return jdbcTemplate.queryForObject("select sum(price) from T_Book where isbn in (?)"
				, Integer.class
				, String.join("','", inISBNList.toArray(new String[0])));
	}

	@Override
	public List<TBook> retrieveBooksByKeyword(final T jdbcTemplate2, String inKeyword) throws SQLException {
		JdbcTemplate jdbcTemplate = jdbcTemplate2 != null ? jdbcTemplate2 : jdbcTemplate3;
		return jdbcTemplate.query(
				new PreparedStatementCreator() {

					@Override
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
						PreparedStatement pst = con.prepareStatement(
								"select id, isbn, title, author, publisher, price from T_Book where author like ? or title like ? or publisher like ?");
						String keyword = "%" + inKeyword + "%";
						pst.setString(1, keyword);
						pst.setString(2, keyword);
						pst.setString(3, keyword);
						return pst;
					}
				}
				, new RowMapper<TBook>() {

					@Override
					public TBook mapRow(ResultSet rs, int rowNum) throws SQLException {
						int id = rs.getInt(1);
						String isbn = rs.getString(2);
						String title = rs.getString(3);
						String author = rs.getString(4);
						String publisher = rs.getString(5);
						int price = rs.getInt(6);

						TBook book = new TBook();
						book.setId(id);
						book.setIsbn(isbn);
						book.setTitle(title);
						book.setAuthor(author);
						book.setPublisher(publisher);
						book.setPrice(price);
						return book;
					}
				});
	}

	@Override
	public List<TBook> retrieveBooksByISBNs(final T jdbcTemplate2, final List<String> inISBNList) throws SQLException {
		JdbcTemplate jdbcTemplate = jdbcTemplate2 != null ? jdbcTemplate2 : jdbcTemplate3;
		return jdbcTemplate.query(
				new PreparedStatementCreator() {

					@Override
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
						PreparedStatement pst;
						if (inISBNList == null || inISBNList.isEmpty()) {
							pst = con.prepareStatement("select id, isbn, title, author, publisher, price from T_Book");
						}
						else {
							pst = con.prepareStatement("select id, isbn, title, author, publisher, price from T_Book where isbn in ('"
									+ String.join("','", inISBNList.toArray(new String[0]))
									+ "')");
						}
						return pst;
					}
				}
				, new RowMapper<TBook>() {

					@Override
					public TBook mapRow(ResultSet rs, int rowNum) throws SQLException {
						int id = rs.getInt(1);
						String isbn = rs.getString(2);
						String title = rs.getString(3);
						String author = rs.getString(4);
						String publisher = rs.getString(5);
						int price = rs.getInt(6);

						TBook book = new TBook();
						book.setId(id);
						book.setIsbn(isbn);
						book.setTitle(title);
						book.setAuthor(author);
						book.setPublisher(publisher);
						book.setPrice(price);
						return book;
					}
				});
	}
}
