package bookstore.logic.spring;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import bookstore.annotation.Log;
import bookstore.annotation.UsedSpring;
import bookstore.dao.BookDAO;
import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.dao.OrderDetailDAO;
import bookstore.logic.AbstractOrderLogic;

@UsedSpring
@Component("LogicOrderImplBId")
public class OrderLogicWrapper extends AbstractOrderLogic {

	@Autowired @Qualifier("BookDAOBId") BookDAO bookdao;
	@Autowired @Qualifier("CustomerDAOBId") CustomerDAO customerdao;
	@Autowired @Qualifier("OrderDAOBId")OrderDAO orderdao;
	@Autowired @Qualifier("OrderDetailDAOBId")OrderDetailDAO odetaildao;
	@Log private static Logger log;

	@Override
	protected BookDAO getBookDAO() {
		return bookdao;
	}
	@Override
	protected CustomerDAO getCustomerDAO() {
		return customerdao;
	}
	@Override
	protected OrderDAO getOrderDAO() {
		return orderdao;
	}
	@Override
	protected OrderDetailDAO getOrderDetailDAO() {
		return odetaildao;
	}
	@Override
	protected Logger getLogger() {
		return log;
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
