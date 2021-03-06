package bookstore.vbean;

import java.io.Serializable;
import java.util.List;

public class VCheckout implements Serializable {


	private static final long serialVersionUID = 1L;

	private int total;
	private List<VBook> selecteditems;

	public int getTotal() {
		return (this.total);
	}

	public void setTotal(int inTotal) {
		this.total = inTotal;
	}

	public List<VBook> getSelecteditems() {
		return selecteditems;
	}

	public void setSelecteditems(List<VBook> inSelecteditems) {
		this.selecteditems = inSelecteditems;
	}
}