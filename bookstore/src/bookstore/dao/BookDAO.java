package bookstore.dao;

import java.util.List;

import bookstore.pbean.TBook;

public interface BookDAO {

	public int getPriceByISBNs(List<String> inISBNList);

	public List<TBook> retrieveBooksByKeyword(String inKeyword);

	public List<TBook> retrieveBooksByISBNs(List<String> inISBNList);

}
