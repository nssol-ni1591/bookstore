package bookstore.vbean;

import java.util.List;


public class VCheckout{
	
	private int total;
	private List selecteditems;

	public int getTotal(){
		return( this.total );
	}
	public void setTotal( int inTotal ){
		this.total = inTotal;
	}
	
	public List getSelecteditems() {
		return selecteditems;
	}
	public void setSelecteditems(List inSelecteditems) {
		this.selecteditems = inSelecteditems;
	}
}