package bookstore.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import bookstore.dao.BookDAO;
import bookstore.pbean.TBook;
import bookstore.vbean.VBook;
import bookstore.vbean.VCheckout;

public abstract class AbstractBookService<T> implements BookService {

	protected abstract BookDAO<T> getBookDAO();
	protected abstract Logger getLogger();
	protected abstract T getManager();

	public List<String> getAllBookISBNs() throws SQLException {
		T em = getManager();
		BookDAO<T> bookdao = getBookDAO();
		Iterator<TBook> iter = bookdao.retrieveBooksByISBNs(em, null).iterator();
		List<String> isbns = new ArrayList<>();

		while (iter.hasNext()) {
			TBook book = iter.next();
			isbns.add(book.getIsbn());
		}

		return isbns;
	}

	public List<String> retrieveBookISBNsByKeyword(String keyword) throws SQLException {
		T em = getManager();
		BookDAO<T> bookdao = getBookDAO();
		Iterator<TBook> iter = bookdao.retrieveBooksByKeyword(em, keyword).iterator();
		List<String> isbns = new ArrayList<>();

		while (iter.hasNext()) {
			TBook book = iter.next();
			isbns.add(book.getIsbn());
		}
		return isbns;
	}

	public List<VBook> createVBookList(List<String> productList, List<String> selectedList) throws SQLException {
		T em = getManager();
		BookDAO<T> bookdao = getBookDAO();
		List<VBook> vArrayList = new ArrayList<>();
		Iterator<TBook> iter = bookdao.retrieveBooksByISBNs(em, productList).iterator();

		while (iter.hasNext()) {
			TBook currentBook = iter.next();
			VBook currentVBook = new VBook(currentBook);

			currentVBook.setSelected(false);

			if (selectedList != null && !selectedList.isEmpty() && selectedList.contains(currentBook.getIsbn())) {
				currentVBook.setSelected(true);
			}

			vArrayList.add(currentVBook);
		}
		return vArrayList;
	}

	public VCheckout createVCheckout(List<String> selectedList) throws SQLException {
		T em = getManager();
		BookDAO<T> bookdao = getBookDAO();
		VCheckout vc = new VCheckout();
		vc.setTotal(bookdao.getPriceByISBNs(em, selectedList));

		List<VBook> viewList = new ArrayList<>();

		Iterator<TBook> iter = bookdao.retrieveBooksByISBNs(em, selectedList).iterator();

		while (iter.hasNext()) {
			TBook currentBook = iter.next();
			VBook vb = new VBook(currentBook);

			vb.setSelected(true);
			viewList.add(vb);
		}
		vc.setSelecteditems(viewList);
		return (vc);
	}

	public List<String> createCart(List<String> productList, List<String> selectedList, List<String> cart) {

		cart.removeAll(productList);
		if (selectedList != null && !selectedList.isEmpty()) {
			cart.addAll(selectedList);
		}
		return (cart);
	}

}
