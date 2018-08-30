package bookstore.vbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import bookstore.pbean.TOrder;

public class VOrder implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private VCustomer vCustomer;

	private Date orderday;

	private List<VOrderDetail> vOrderDetails = new ArrayList<>();

	public VOrder() {
	}

	public VOrder(TOrder order) {
		id = order.getId();
		vCustomer = new VCustomer(order.getTCustomer());
		orderday = order.getOrderday();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public VCustomer getVCustomer() {
		return vCustomer;
	}

	public void setVCustomer(VCustomer vCustomer) {
		this.vCustomer = vCustomer;
	}

	public Date getOrderday() {
		return orderday;
	}

	public void setOrderday(Date orderday) {
		this.orderday = orderday;
	}

	public List<VOrderDetail> getVOrderDetails() {
		return vOrderDetails;
	}

	public void setVOrderDetails(List<VOrderDetail> vOrderDetails) {
		this.vOrderDetails = vOrderDetails;
	}
}
