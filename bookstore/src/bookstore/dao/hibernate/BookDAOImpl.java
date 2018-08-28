package bookstore.dao.hibernate;

import java.util.List;
import java.util.regex.Pattern;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import bookstore.dao.BookDAO;

public class BookDAOImpl
  extends HibernateDaoSupport
  implements BookDAO{

  public int getPriceByISBNs(final List inISBNList) {
    HibernateTemplate ht = getHibernateTemplate();
    return( ((Long)ht.execute(new HibernateCallback() {
	public Object doInHibernate(Session session)
	  throws HibernateException {
	  Query priceQuery = session
	    .createQuery("select sum( book.price ) from TBook book where book.isbn in ( :SELECTED_ITEMS )");
	  priceQuery.setParameterList("SELECTED_ITEMS", inISBNList);

	  try {
	    Thread.currentThread().sleep(10000);
	  }
	  catch (InterruptedException ex) { }
	  return( (Long)priceQuery.uniqueResult() );
	}
      } )).intValue() );
  }


  public List retrieveBooksByKeyword(String inKeyword) {
    String escapedKeyword = Pattern.compile( "([%_])" )
      .matcher( inKeyword )
      .replaceAll( "\\\\$1" );
    String[] keywords = { "%" + escapedKeyword + "%",
			  "%" + escapedKeyword + "%",
			  "%" + escapedKeyword + "%" };

    List booksList = getHibernateTemplate().find("from TBook book where book.author like ?" +
						 "or book.title like ? or book.publisher like ?" ,
						 keywords );
    return( booksList );
  }


  public List retrieveBooksByISBNs( final List inISBNList ){

    HibernateTemplate ht = getHibernateTemplate();
		
    if( inISBNList == null ){
      return( ht.find( "from TBook book" ) );
    }else{
		
      return( ((List)ht.execute(new HibernateCallback() {

	  public Object doInHibernate(Session session)
	    throws HibernateException {

	    Query retrieveQuery = session
	      .createQuery("from TBook book where book.isbn in ( :ISBNS )");
	    retrieveQuery.setParameterList("ISBNS", inISBNList);

	    System.out.println("inISBNList=\"" + inISBNList + "\"");
	    System.out.println("retrieveQuery=\"" + retrieveQuery + "\"");

	    try {
	      Thread.currentThread().sleep(10000);
	    }
	    catch (InterruptedException ex) { }
	    return( retrieveQuery.list() );
	  }
	} )));
    }
  }
}
