package bookstore.logic.spring.jtatx;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

/*
 * �g�����U�N�V�����}�l�[�W����JtaTransactionManager���g�p����ꍇ�F
 *
 * ���̃N���X�ł͊�����@Autowaired���g�p����
 */
@UsedSpring
@Component("LogicOrderImplBId3")
public class OrderLogicWrapper extends AbstractOrderLogic<JdbcTemplate> {

	@Log private static Logger log;

	@Autowired @Qualifier("BookDAOBId2") BookDAO<JdbcTemplate> bookdao;
	@Autowired @Qualifier("CustomerDAOBId2") CustomerDAO<JdbcTemplate> customerdao;
	@Autowired @Qualifier("OrderDAOBId2") OrderDAO<JdbcTemplate> orderdao;
	@Autowired @Qualifier("OrderDetailDAOBId2") OrderDetailDAO<JdbcTemplate> odetaildao;
	@Autowired @Qualifier("jdbcTemplate3") JdbcTemplate jdbcTemplate;

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
		//return null;
	}


	@Override
	@Transactional(value="jtatx", propagation=Propagation.REQUIRED)
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
	@Transactional(value="jtatx", propagation=Propagation.REQUIRED, readOnly=true)
	public List<VOrder> listOrders(List<String> orderIdList) throws SQLException {
		return super.listOrders(orderIdList);
	}

}
