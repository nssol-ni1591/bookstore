package bookstore.service.spring.dstx;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import bookstore.annotation.Log;
import bookstore.annotation.UsedSpring;
import bookstore.dao.BookDAO;
import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.dao.OrderDetailDAO;
import bookstore.service.AbstractOrderService;
import bookstore.service.spring.SpringRuntimeException;
import bookstore.vbean.VOrder;

/*
 * �g�����U�N�V�����}�l�[�W����DataSourceTransactionManager���g�p����ꍇ�F
 * 
 * ���̃N���X�ł͊�����@Autowaired���g�p����. DI���邽�߂�setter����������
 */
@UsedSpring
@Component("ServiceOrderImplBId2")
public class OrderServiceWrapper extends AbstractOrderService<JdbcTemplate> {

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
		// null�̏ꍇ�AjdbcTemplate�Ƃ��Ă͓����C���X�^���X�������n����邪�A
		// Connection��leak���������Ă���悤��
		//return null
	}

	// DI���������邽�߂�Setter���\�b�hs
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
	//@Transactional(propagation=Propagation.REQUIRED)	//���̎w��͖����BapplicationContext.xml�̐ݒ肪�D��
	public void orderBooks(String inUid, List<String> inISBNs) throws Exception {
		log.log(Level.INFO, "datasource={0}"
				, jdbcTemplate == null ? "null" : jdbcTemplate.getDataSource().getClass().getName());

		//rollback���邽�߂̗�O��RuntimeException�łȂ��Ƃ����Ȃ�
		try {
			super.orderBooks(inUid, inISBNs);
		}
		catch (RuntimeException e) {
			throw e;
		}
		catch (Exception e) {
			throw new SpringRuntimeException(e);
		}
	}

	@Override
	//@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
	public List<VOrder> listOrders(List<String> orderIdList) throws SQLException {
		return super.listOrders(orderIdList);
	}

}
