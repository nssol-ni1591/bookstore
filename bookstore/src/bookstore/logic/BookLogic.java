package bookstore.logic;

import java.sql.SQLException;
import java.util.List;

import bookstore.vbean.VBook;
import bookstore.vbean.VCheckout;

public interface BookLogic {

	public List<String> getAllBookISBNs() throws SQLException;

	public List<String> retrieveBookISBNsByKeyword(String inKeyword) throws SQLException;

	public List<VBook> createVBookList(List<String> inProductList, List<String> inSelectedList) throws SQLException;

	public VCheckout createVCheckout(List<String> inSelectedList) throws SQLException;

	public List<String> createCart(List<String> inProductList, List<String> inSelectedList, List<String> inCart);

}
