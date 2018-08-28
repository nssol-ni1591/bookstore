package bookstore.logic;

import java.util.List;

import bookstore.vbean.VBook;
import bookstore.vbean.VCheckout;

public interface BookLogic {

	public List<String> getAllBookISBNs();

	public List<String> retrieveBookISBNsByKeyword(String inKeyword);

	public List<VBook> createVBookList(List<String> inProductList, List<String> inSelectedList);

	public VCheckout createVCheckout(List<String> inSelectedList);

	public List<String> createCart(List<String> inProductList, List<String> inSelectedList, List<String> inCart);

}
