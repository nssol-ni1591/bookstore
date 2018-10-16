package bookstore.service;

import java.sql.SQLException;
import java.util.List;

import bookstore.vbean.VBook;
import bookstore.vbean.VCheckout;

public interface BookService {

	public List<String> getAllBookISBNs() throws SQLException;

	public List<String> retrieveBookISBNsByKeyword(String keyword) throws SQLException;

	public List<VBook> createVBookList(List<String> productList, List<String> selectedList) throws SQLException;

	public VCheckout createVCheckout(List<String> selectedList) throws SQLException;

	public List<String> createCart(List<String> productList, List<String> selectedList, List<String> cart);

}
