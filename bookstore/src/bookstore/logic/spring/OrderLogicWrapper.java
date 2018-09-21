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
public class OrderLogicWrapper extends AbstractOrderLogic<Object> {

	@Autowired @Qualifier("BookDAOBId") BookDAO<Object> bookdao;
	@Autowired @Qualifier("CustomerDAOBId") CustomerDAO<Object> customerdao;
	@Autowired @Qualifier("OrderDAOBId")OrderDAO<Object> orderdao;
	@Autowired @Qualifier("OrderDetailDAOBId")OrderDetailDAO<Object> odetaildao;
	@Log private static Logger log;

	@Override
	protected BookDAO<Object> getBookDAO() {
		return bookdao;
	}
	@Override
	protected CustomerDAO<Object> getCustomerDAO() {
		return customerdao;
	}
	@Override
	protected OrderDAO<Object> getOrderDAO() {
		return orderdao;
	}
	@Override
	protected OrderDetailDAO<Object> getOrderDetailDAO() {
		return odetaildao;
	}
	@Override
	protected Logger getLogger() {
		return log;
	}
	@Override
	protected Object getManager() {
		return null;
	}


	public void setBookdao(BookDAO<Object> bookdao) {
		this.bookdao = bookdao;
	}
	public void setCustomerdao(CustomerDAO<Object> customerdao) {
		this.customerdao = customerdao;
	}
	public void setOrderdao(OrderDAO<Object> orderdao) {
		this.orderdao = orderdao;
	}
	public void setOrderdetaildao(OrderDetailDAO<Object> odetaildao) {
		this.odetaildao = odetaildao;
	}

}
