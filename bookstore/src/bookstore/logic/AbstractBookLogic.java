package bookstore.logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import bookstore.dao.BookDAO;
import bookstore.pbean.TBook;
import bookstore.vbean.VBook;
import bookstore.vbean.VCheckout;

public abstract class AbstractBookLogic implements BookLogic {

	protected abstract BookDAO getBookDAO();
	protected abstract Logger getLogger();

	public List<String> getAllBookISBNs() throws SQLException {
		BookDAO bookdao = getBookDAO();
		Iterator<TBook> iter = bookdao.retrieveBooksByISBNs(null).iterator();
		List<String> isbns = new ArrayList<>();

		while (iter.hasNext()) {
			TBook book = iter.next();
			isbns.add(book.getIsbn());
		}

		return isbns;
	}

	public List<String> retrieveBookISBNsByKeyword(String inKeyword) throws SQLException {
		BookDAO bookdao = getBookDAO();
		Iterator<TBook> iter = bookdao.retrieveBooksByKeyword(inKeyword).iterator();
		List<String> isbns = new ArrayList<>();

		while (iter.hasNext()) {
			TBook book = iter.next();
			isbns.add(book.getIsbn());
		}
		return isbns;
	}

	public List<VBook> createVBookList(List<String> inProductList, List<String> inSelectedList) throws SQLException {
		BookDAO bookdao = getBookDAO();
		List<VBook> vArrayList = new ArrayList<>();
		Iterator<TBook> iter = bookdao.retrieveBooksByISBNs(inProductList).iterator();

		while (iter.hasNext()) {
			TBook currentBook = iter.next();
			VBook currentVBook = new VBook(currentBook);

			currentVBook.setSelected(false);

			if (inSelectedList != null && !inSelectedList.isEmpty() && inSelectedList.contains(currentBook.getIsbn())) {
				currentVBook.setSelected(true);
			}

			vArrayList.add(currentVBook);
		}
		return vArrayList;
	}

	public VCheckout createVCheckout(List<String> inSelectedList) throws SQLException {
		BookDAO bookdao = getBookDAO();
		VCheckout vc = new VCheckout();
		vc.setTotal(bookdao.getPriceByISBNs(inSelectedList));

		List<VBook> viewList = new ArrayList<>();

		Iterator<TBook> iter = bookdao.retrieveBooksByISBNs(inSelectedList).iterator();

		while (iter.hasNext()) {
			TBook currentBook = iter.next();
			VBook vb = new VBook(currentBook);

			vb.setSelected(true);
			viewList.add(vb);
		}
		vc.setSelecteditems(viewList);
		return (vc);
	}

	public List<String> createCart(List<String> inProductList, List<String> inSelectedList, List<String> inCart) {

		inCart.removeAll(inProductList);
		if (inSelectedList != null && !inSelectedList.isEmpty()) {
			inCart.addAll(inSelectedList);
		}
		return (inCart);
	}

}
