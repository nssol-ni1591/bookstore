package bookstore.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookstore.dao.BookDAO;
import bookstore.pbean.TBook;

public class BookDAOImpl<T extends Connection> implements BookDAO<T> {

	@Override
	public int getPriceByISBNs(final T con2, final List<String> inISBNList) throws SQLException {
		Connection con = con2 != null ? con2 : DB.createConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = con.prepareStatement("select sum(price) from T_Book where isbn in ('" 
					+ String.join("','", inISBNList.toArray(new String[0]))
					+ "')");
			rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		}
		finally {
			DB.close(rs, pst, con2 != null ? null : con);
		}
		return 0;
	}

	@Override
	public List<TBook> retrieveBooksByKeyword(final T con2, String inKeyword) throws SQLException {
		Connection con = con2 != null ? con2 : DB.createConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<TBook> booksList = new ArrayList<>();
		try {
			pst = con.prepareStatement("select id, isbn, title, author, publisher, price from T_Book where author like ? or title like ? or publisher like ?");
			String keyword = "%" + inKeyword + "%";
			pst.setString(1, keyword);
			pst.setString(2, keyword);
			pst.setString(3, keyword);
			rs = pst.executeQuery();
			while (rs.next()) {
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
				booksList.add(book);
			}
		}
		finally {
			DB.close(rs, pst, con2 != null ? null : con);
		}
		return booksList;
	}

	@Override
	public List<TBook> retrieveBooksByISBNs(final T con2, final List<String> inISBNList) throws SQLException {
		Connection con = con2 != null ? con2 : DB.createConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<TBook> booksList = new ArrayList<>();
		try {
			if (inISBNList == null || inISBNList.isEmpty()) {
				pst = con.prepareStatement("select id, isbn, title, author, publisher, price from T_Book");
			}
			else {
				pst = con.prepareStatement("select id, isbn, title, author, publisher, price from T_Book where isbn in ('"
						+ String.join("','", inISBNList.toArray(new String[0]))
						+ "')");
			}
			rs = pst.executeQuery();
			while (rs.next()) {
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
				booksList.add(book);
			}
		}
		finally {
			DB.close(rs, pst, con2 != null ? null : con);
		}
		return booksList;
	}
}
