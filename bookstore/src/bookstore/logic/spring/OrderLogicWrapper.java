package bookstore.logic.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import bookstore.annotation.UsedSpring;
import bookstore.dao.BookDAO;
import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.dao.OrderDetailDAO;
import bookstore.logic.impl.OrderLogicImpl;

@UsedSpring
@Component("LogicOrderImplBId")
public class OrderLogicWrapper extends OrderLogicImpl {

	@Autowired @Qualifier("BookDAOBId") BookDAO bookdao;
	@Autowired @Qualifier("CustomerDAOBId") CustomerDAO customerdao;
	@Autowired @Qualifier("OrderDAOBId")OrderDAO orderdao;
	@Autowired @Qualifier("OrderDetailDAOBId")OrderDetailDAO odetaildao;

	public OrderLogicWrapper() {
		super.setBookdao(bookdao);
		super.setCustomerdao(customerdao);
		super.setOrderdao(orderdao);
		super.setOrderdetaildao(odetaildao);
	}

	public void setBookdao(BookDAO bookdao) {
		this.bookdao = bookdao;
		super.setBookdao(bookdao);
	}
	public void setCustomerdao(CustomerDAO customerdao) {
		this.customerdao = customerdao;
		super.setCustomerdao(customerdao);
	}
	public void setOrderdao(OrderDAO orderdao) {
		this.orderdao = orderdao;
		super.setOrderdao(orderdao);
	}
	public void setOrderdetaildao(OrderDetailDAO odetaildao) {
		this.odetaildao = odetaildao;
		super.setOrderdetaildao(odetaildao);
	}

}
