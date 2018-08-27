package bookstore.logic;

import java.util.List;

import bookstore.vbean.VCheckout;

public interface BookLogic{
	public List getAllBookISBNs();
	public List retrieveBookISBNsByKeyword( String inKeyword );
	public List createVBookList( List inProductList,
								  List inSelectedList );
	public VCheckout createVCheckout( List inSelectedList );
	public List createCart( List inProductList, 
							 List inSelectedList,
							 List inCart  );
}