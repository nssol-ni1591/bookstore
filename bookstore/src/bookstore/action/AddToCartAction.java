package bookstore.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import bookstore.action.bean.AddToCartActionFormBean;
import bookstore.logic.BookLogic;

public class AddToCartAction extends Action {
	
	BookLogic bookLogic;
	
	public ActionForward execute( ActionMapping mapping,
								   ActionForm form,
								   HttpServletRequest req,
								   HttpServletResponse res ){

		HttpSession httpSession = req.getSession( false );
		if( httpSession == null ){
			return( mapping.findForward( "illegalSession" ) );
		}
		
		List cart = (List) httpSession.getAttribute( "Cart" );
		if( cart == null ){
			cart = new ArrayList();
		}
		
		AddToCartActionFormBean atcafb = (AddToCartActionFormBean)form;
		
		List productList = (List)httpSession.getAttribute( "ProductList" );
		
		String[] selecteItemsArray = atcafb.getSelecteditems();
		List selectedItems = null;

		if( selecteItemsArray != null &&
				selecteItemsArray.length != 0 ){
			selectedItems = Arrays.asList( atcafb.getSelecteditems() );
		}
		
		List newCart = bookLogic.createCart( productList,
											 selectedItems,
											 cart );
		
		httpSession.setAttribute( "Cart", newCart );

		List productListAll = bookLogic.getAllBookISBNs();
		List vProductList = bookLogic.createVBookList(
										productListAll, newCart );
		
		httpSession.setAttribute( "ProductList", productListAll );
		httpSession.setAttribute( "ProductListView", vProductList );
		
		return( mapping.findForward( "Continue" ) );
	}
	

	public void setBookLogic( BookLogic bookLogic ){
		this.bookLogic = bookLogic;
	}
}