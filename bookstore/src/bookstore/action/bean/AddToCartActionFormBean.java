package bookstore.action.bean;

import org.apache.struts.action.ActionForm;

public class AddToCartActionFormBean extends ActionForm{

	private String[] selecteditems;
	
	public String[] getSelecteditems(){
		return( this.selecteditems );
	}
	public void setSelecteditems( String[] inSelecteditems ){
		this.selecteditems = inSelecteditems;
	}
}