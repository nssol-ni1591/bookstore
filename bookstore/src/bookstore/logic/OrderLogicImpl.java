package bookstore.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import bookstore.dao.BookDAO;
import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.dao.OrderDetailDAO;
import bookstore.pbean.TBook;
import bookstore.pbean.TCustomer;
import bookstore.pbean.TOrder;
import bookstore.vbean.VOrder;

@Component("LogicOrderImplBId")
public class OrderLogicImpl implements OrderLogic {

	@Autowired @Qualifier("BookDAOBId") BookDAO bookdao;
	@Autowired @Qualifier("CustomerDAOBId") CustomerDAO customerdao;
	@Autowired @Qualifier("OrderDAOBId")OrderDAO orderdao;
	@Autowired @Qualifier("OrderDetailDAOBId")OrderDetailDAO odetaildao;

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
		Iterator<TOrder> iter = orderdao.retrieveOrders(null).iterator();

		while (iter.hasNext()) {
			TOrder currentOrder = iter.next();
			VOrder currentVOrder = new VOrder(currentOrder);
			orderList.add(currentVOrder);
		}
		return orderList;
	}

	public void setBookdao(BookDAO bookdao) {
		this.bookdao = bookdao;
	}

	public void setCustomerdao(CustomerDAO customerdao) {
		this.customerdao = customerdao;
	}

	public void setOrderdao(OrderDAO orderdao) {
		this.orderdao = orderdao;
	}

	public void setOrderdetaildao(OrderDetailDAO odetaildao) {
		this.odetaildao = odetaildao;
	}

}
