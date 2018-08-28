package bookstore.vbean;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Date;

import bookstore.pbean.TCustomer;
import bookstore.pbean.TOrder;
import bookstore.pbean.TOrderDetail;

public class VOrder {

	private int id;

	private VCustomer vCustomer;
	private String uid;
	private String name;
	private String email;

	private Date orderday;

	private List vOrderDetails = new ArrayList();
	// - id -> this.id
	// - order -> this
	// - book
	private int bookId;
	private String isbn;
	private String title;
	private String author;
	private String publisher;
	private int price;

	public VOrder() {
	}

	public VOrder(TOrder order) {
		id = order.getId();
		vCustomer = new VCustomer(order.getTCustomer());
		orderday = order.getOrderday();
		/*
		 * Iterator iter = order.getTOrderDetails().iterator(); while(iter.hasNext()){
		 * TOrderDetail orderDetail = (TOrderDetail)iter.next(); VOrderDetail
		 * vOrderDetail = new VOrderDetail(orderDetail.getTOrderDetail());
		 * vOrderDetails.add(vOrderDetail); }
		 */
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

	public List getVOrderDetails() {
		return vOrderDetails;
	}

	public void setVOrderDetails(List vOrderDetails) {
		this.vOrderDetails = vOrderDetails;
	}
}
