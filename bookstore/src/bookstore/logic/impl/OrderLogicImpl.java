package bookstore.logic.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bookstore.dao.BookDAO;
import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.dao.OrderDetailDAO;
import bookstore.logic.OrderLogic;
import bookstore.pbean.TBook;
import bookstore.pbean.TCustomer;
import bookstore.pbean.TOrder;
import bookstore.pbean.TOrderDetail;
import bookstore.vbean.VOrder;
import bookstore.vbean.VOrderDetail;

public class OrderLogicImpl implements OrderLogic {

	private BookDAO bookdao;
	private CustomerDAO customerdao;
	private OrderDAO orderdao;
	private OrderDetailDAO odetaildao;

	public void orderBooks(String inUid, List<String> inISBNs) {

		TCustomer customer = customerdao.findCustomerByUid(inUid);
		TOrder order = orderdao.createOrder(customer);

		Iterator<TBook> iter = bookdao.retrieveBooksByISBNs(inISBNs).iterator();
		while (iter.hasNext()) {
			TBook book = iter.next();
			odetaildao.createOrderDetail(order, book);
		}
	}

	public List<VOrder> listOrders(List<String> orderIdList) {
		List<VOrder> orderList = new ArrayList<>();
		Iterator<TOrder> iter = orderdao.retrieveOrders(orderIdList).iterator();

		while (iter.hasNext()) {
			TOrder currentOrder = iter.next();
			VOrder currentVOrder = new VOrder(currentOrder);
			orderList.add(currentVOrder);
		}
		return orderList;
	}

	public List<VOrderDetail> listOrderDetails(List<String> orders) {
		List<VOrderDetail> list = new ArrayList<>();
		Iterator<TOrderDetail> iter = odetaildao.listOrderDetails(orders).iterator();

		while (iter.hasNext()) {
			TOrderDetail currentOrder = iter.next();
			VOrderDetail currentVOrder = new VOrderDetail(currentOrder);
			list.add(currentVOrder);
		}
		return list;
	}

	protected void setBookdao(BookDAO bookdao) {
		this.bookdao = bookdao;
	}

	protected void setCustomerdao(CustomerDAO customerdao) {
		this.customerdao = customerdao;
	}

	protected void setOrderdao(OrderDAO orderdao) {
		this.orderdao = orderdao;
	}

	protected void setOrderdetaildao(OrderDetailDAO odetaildao) {
		this.odetaildao = odetaildao;
	}

}
