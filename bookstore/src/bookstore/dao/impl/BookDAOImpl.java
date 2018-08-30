package bookstore.dao.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import bookstore.dao.BookDAO;
import bookstore.pbean.TBook;

public class BookDAOImpl implements BookDAO {

	@Override
	public int getPriceByISBNs(final List<String> inISBNList) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			con = DB.createConnection ();
			pst = con.prepareStatement("select sum(price) from T_Book where isbn in ('" 
					+ String.join("','", inISBNList.toArray(new String[0]))
					+ "')");
			rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		}
		catch (ClassNotFoundException | IOException | SQLException e) {
			Logger.getLogger(BookDAOImpl.class.getName()).log(Level.SEVERE, "", e);
		}
		finally {
			if (rs != null ) {
				try {
					rs.close();
				}
				catch (SQLException e) {
					Logger.getLogger(BookDAOImpl.class.getName()).log(Level.SEVERE, e.getMessage());
				}
			}
			if (pst != null) {
				try {
					pst.close();
				}
				catch (SQLException e) {
					Logger.getLogger(BookDAOImpl.class.getName()).log(Level.SEVERE, e.getMessage());
				}
			}
			if (con != null) {
				try {
					con.close();
				}
				catch (SQLException e) {
					Logger.getLogger(BookDAOImpl.class.getName()).log(Level.SEVERE, e.getMessage());
				}
			}
		}
		return 0;
	}

	@Override
	public List<TBook> retrieveBooksByKeyword(String inKeyword) {
		String keyword = "%" + inKeyword + "%";
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<TBook> booksList = new ArrayList<>();
		try {
			con = DB.createConnection ();
			pst = con.prepareStatement("select id, isbn, title, author, publisher, price from T_Book where author like ? or title like ? or publisher like ?");
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
				booksList.add(new TBook(id, isbn, title, author, publisher, price));
			}
		}
		catch (ClassNotFoundException | IOException | SQLException e) {
			Logger.getLogger(BookDAOImpl.class.getName()).log(Level.SEVERE, "", e);
		}
		finally {
			if (rs != null ) {
				try {
					rs.close();
				}
				catch (SQLException e) {
					Logger.getLogger(BookDAOImpl.class.getName()).log(Level.SEVERE, e.getMessage());
				}
			}
			if (pst != null) {
				try {
					pst.close();
				}
				catch (SQLException e) {
					Logger.getLogger(BookDAOImpl.class.getName()).log(Level.SEVERE, e.getMessage());
				}
			}
			if (con != null) {
				try {
					con.close();
				}
				catch (SQLException e) {
					Logger.getLogger(BookDAOImpl.class.getName()).log(Level.SEVERE, e.getMessage());
				}
			}
		}
		return booksList;
	}

	@Override
	public List<TBook> retrieveBooksByISBNs(final List<String> inISBNList) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<TBook> booksList = new ArrayList<>();
		try {
			con = DB.createConnection ();
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
				booksList.add(new TBook(id, isbn, title, author, publisher, price));
			}
		}
		catch (ClassNotFoundException | IOException | SQLException e) {
			Logger.getLogger(BookDAOImpl.class.getName()).log(Level.SEVERE, "", e);
		}
		finally {
			if (rs != null ) {
				try {
					rs.close();
				}
				catch (SQLException e) {
					Logger.getLogger(BookDAOImpl.class.getName()).log(Level.SEVERE, e.getMessage());
				}
			}
			if (pst != null) {
				try {
					pst.close();
				}
				catch (SQLException e) {
					Logger.getLogger(BookDAOImpl.class.getName()).log(Level.SEVERE, e.getMessage());
				}
			}
			if (con != null) {
				try {
					con.close();
				}
				catch (SQLException e) {
					Logger.getLogger(BookDAOImpl.class.getName()).log(Level.SEVERE, e.getMessage());
				}
			}
		}
		return booksList;
	}
}
