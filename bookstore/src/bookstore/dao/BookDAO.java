package bookstore.dao;

import java.sql.SQLException;
import java.util.List;

import bookstore.pbean.TBook;

public interface BookDAO {

	public int getPriceByISBNs(List<String> inISBNList) throws SQLException;

	public List<TBook> retrieveBooksByKeyword(String inKeyword) throws SQLException;

	public List<TBook> retrieveBooksByISBNs(List<String> inISBNList) throws SQLException;

}
