package bookstore.dao;

import java.util.List;

public interface BookDAO{
	public int getPriceByISBNs( List inISBNList );
	public List retrieveBooksByKeyword( String inKeyword );
	public List retrieveBooksByISBNs( List inISBNList );
}