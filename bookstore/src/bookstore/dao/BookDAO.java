package bookstore.dao;

import java.sql.SQLException;
import java.util.List;

import bookstore.pbean.TBook;

public interface BookDAO<T> {

	public int getPriceByISBNs(final T em, List<String> inISBNList) throws SQLException;

	public List<TBook> retrieveBooksByKeyword(final T em, String inKeyword) throws SQLException;

	public List<TBook> retrieveBooksByISBNs(final T em, List<String> inISBNList) throws SQLException;

}
