package bookstore.logic.spring.legacy;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import bookstore.annotation.Log;
import bookstore.annotation.UsedSpring;
import bookstore.dao.BookDAO;
import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.dao.OrderDetailDAO;
import bookstore.logic.AbstractOrderLogic;
import bookstore.logic.spring.SpringRuntimeException;
import bookstore.vbean.VOrder;

@UsedSpring
@Component("LogicOrderImplBId2")
public class OrderLogicWrapper extends AbstractOrderLogic<JdbcTemplate> {

	@Log private static Logger log;

	private BookDAO<JdbcTemplate> bookdao;
	private CustomerDAO<JdbcTemplate> customerdao;
	private OrderDAO<JdbcTemplate> orderdao;
	private OrderDetailDAO<JdbcTemplate> odetaildao;
	private JdbcTemplate jdbcTemplate;

	@Override
	protected BookDAO<JdbcTemplate> getBookDAO() {
		return bookdao;
	}
	@Override
	protected CustomerDAO<JdbcTemplate> getCustomerDAO() {
		return customerdao;
	}
	@Override
	protected OrderDAO<JdbcTemplate> getOrderDAO() {
		return orderdao;
	}
	@Override
	protected OrderDetailDAO<JdbcTemplate> getOrderDetailDAO() {
		return odetaildao;
	}
	@Override
	protected Logger getLogger() {
		return log;
	}
	@Override
	protected JdbcTemplate getManager() {
		return jdbcTemplate;
	}

	public void setBookdao(BookDAO<JdbcTemplate> bookdao) {
		this.bookdao = bookdao;
	}
	public void setCustomerdao(CustomerDAO<JdbcTemplate> customerdao) {
		this.customerdao = customerdao;
	}
	public void setOrderdao(OrderDAO<JdbcTemplate> orderdao) {
		this.orderdao = orderdao;
	}
	public void setOrderdetaildao(OrderDetailDAO<JdbcTemplate> odetaildao) {
		this.odetaildao = odetaildao;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)	//-> applicationContext.xmlÇ…ìØìôÇÃíËã`Ç†ÇËÅH
	public void orderBooks(String inUid, List<String> inISBNs) throws Exception {
		//rollbackÇ∑ÇÈÇΩÇﬂÇÃó·äOÇÕRuntimeExceptionÇ≈Ç»Ç¢Ç∆Ç¢ÇØÇ»Ç¢
		try {
			super.orderBooks(inUid, inISBNs);
		}
		catch (Exception e) {
			log.log(Level.SEVERE, "e={0}", new Object[] { e.getMessage() });
			if (e instanceof RuntimeException) {
				throw e;
			}
			throw new SpringRuntimeException(e);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public List<VOrder> listOrders(List<String> orderIdList) throws SQLException {
		return super.listOrders(orderIdList);
	}

}
