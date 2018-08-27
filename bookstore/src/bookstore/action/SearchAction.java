package bookstore.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import bookstore.action.bean.SearchActionFormBean;
import bookstore.logic.BookLogic;

public class SearchAction extends Action{

	BookLogic bookLogic;
	
	public ActionForward execute( ActionMapping mapping,
								   ActionForm form,
								   HttpServletRequest req,
								   HttpServletResponse res ){

		HttpSession httpSession = req.getSession( false );
		if( httpSession == null ){
			return( mapping.findForward( "illegalSession" ) );
		}
		
		ActionMessages errors;
		
		List cart = (List) httpSession.getAttribute( "Cart" );
		
		SearchActionFormBean safb = (SearchActionFormBean)form;
		
		List foundBooks = bookLogic.retrieveBookISBNsByKeyword( safb.getKeyword() );
		
		if( foundBooks == null || foundBooks.size() == 0 ){
			
			foundBooks = bookLogic.getAllBookISBNs();
			
			errors = new ActionMessages();
			errors.add( "productalart",
					new ActionMessage( "error.search.notfound" ) );
			saveMessages( req, errors );
		}
		
		List vProductList = bookLogic.createVBookList(
											foundBooks, cart );

		httpSession.setAttribute( "ProductList", foundBooks );
		httpSession.setAttribute( "ProductListView", vProductList );		
				
		return( mapping.findForward( "SearchSuccess" ) );
	}
	
	
	public void setBookLogic(BookLogic bookLogic) {
		this.bookLogic = bookLogic;
	}
}