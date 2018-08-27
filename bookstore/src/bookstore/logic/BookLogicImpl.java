package bookstore.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bookstore.dao.BookDAO;
import bookstore.pbean.TBook;
import bookstore.vbean.VBook;
import bookstore.vbean.VCheckout;

public class BookLogicImpl implements BookLogic{

	BookDAO bookdao;

	public List getAllBookISBNs() {

		Iterator iter = bookdao.retrieveBooksByISBNs( null ).iterator();
		List<String> isbns = new ArrayList<String>();

		while( iter.hasNext() ){
			TBook book = (TBook)iter.next();
			isbns.add( book.getIsbn() );
		}

		return( isbns );
	}


	public List retrieveBookISBNsByKeyword(String inKeyword) {

		Iterator iter = bookdao.retrieveBooksByKeyword( inKeyword ).iterator();
		List<String> isbns = new ArrayList<String>();

		while( iter.hasNext() ){
			TBook book = (TBook)iter.next();
			isbns.add( book.getIsbn() );
		}

		return( isbns );
	}


	public List createVBookList(List inProductList, List inSelectedList) {

		List<VBook> vArrayList = new ArrayList<VBook>();
		Iterator iter = bookdao.retrieveBooksByISBNs( inProductList ).iterator();

		while( iter.hasNext() ){
			TBook currentBook  = (TBook)iter.next();
			VBook currentVBook = new VBook( currentBook );

			currentVBook.setSelected( false );

			if( inSelectedList != null && inSelectedList.size() != 0 ){
				if( inSelectedList.contains( currentBook.getIsbn() ) ){
					currentVBook.setSelected( true );
				}
			}

			vArrayList.add( currentVBook );
		}
		return( vArrayList );
	}


	public VCheckout createVCheckout( List inSelectedList ){

		VCheckout vc = new VCheckout();
		vc.setTotal( bookdao.getPriceByISBNs( inSelectedList ) );

		List<VBook> viewList = new ArrayList<VBook>();
		
		Iterator iter = bookdao.retrieveBooksByISBNs( inSelectedList ).iterator();
		
		while( iter.hasNext() ){
			TBook currentBook = (TBook)iter.next();
			VBook vb = new VBook( currentBook );

			vb.setSelected(true );
			viewList.add( vb );
		}
		vc.setSelecteditems( viewList );
		return( vc );
	}


	public List createCart( List inProductList, 
							 List inSelectedList,
							 List inCart ){

		inCart.removeAll( inProductList );
		if( inSelectedList != null &&
			inSelectedList.size() != 0 ){
			inCart.addAll( inSelectedList );
		}
		return( inCart );
	}


	public void setBookdao( BookDAO bookdao ){
		this.bookdao = bookdao;
	}
}
