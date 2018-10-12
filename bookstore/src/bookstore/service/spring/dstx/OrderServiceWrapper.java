package bookstore.service.spring.dstx;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import bookstore.annotation.Log;
import bookstore.annotation.UsedSpring;
import bookstore.dao.BookDAO;
import bookstore.dao.CustomerDAO;
import bookstore.dao.OrderDAO;
import bookstore.dao.OrderDetailDAO;
import bookstore.service.AbstractOrderService;

/*
 * �g�����U�N�V�����}�l�[�W����DataSourceTransactionManager���g�p����ꍇ�F
 * DataSourceTransactionManager���g�p���Ă���̂ŁA�ȉ��̂ǂꂩ��Tx���䂷��
 * (1)�R���e�L�X�gxml�ł̃g�����U�N�V������` @see: bookstore.service.spring.OrderServiceWrapper
 * (2)@Transactional�ɂ��g�����U�N�V������` @see: bookstore.service.spring.jatx.OrderServiceWrapper
 * (3)�����Ńg�����U�N�V��������
 * �����ł́A(3)�̕��@�Ńg�����U�N�V�����𐧌䂷��
 */
@UsedSpring
@Component("ServiceOrderImplBId2")
public class OrderServiceWrapper extends AbstractOrderService<JdbcTemplate> {

	@Log private static Logger log;

	@Autowired @Qualifier("BookDAOBId2") BookDAO<JdbcTemplate> bookdao;
	@Autowired @Qualifier("CustomerDAOBId2") CustomerDAO<JdbcTemplate> customerdao;
	@Autowired @Qualifier("OrderDAOBId2") OrderDAO<JdbcTemplate> orderdao;
	@Autowired @Qualifier("OrderDetailDAOBId2") OrderDetailDAO<JdbcTemplate> odetaildao;
	@Autowired @Qualifier("jdbcTemplate2") JdbcTemplate jdbcTemplate;
	@Autowired @Qualifier("dstx") PlatformTransactionManager txMgr;

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


	//������Tx������s���Ă���̂ŃR���e�L�X�gxml��Tx��`��@Transactional���K�v�Ȃ�
	@Override
	public void orderBooks(String uid, List<String> isdns) throws SQLException {
		log.log(Level.INFO, "datasource={0}"
				, jdbcTemplate == null ? "null" : jdbcTemplate.getDataSource().getClass().getName());

		// Non-managed environment idiom
		DefaultTransactionDefinition txDef = new DefaultTransactionDefinition();
		TransactionStatus status = txMgr.getTransaction(txDef);
		try {
			super.orderBooks(uid, isdns);
			txMgr.commit(status);
		}
		catch (Exception e) {
			txMgr.rollback(status);
			throw e;
		}
		finally {
			// Do nothing.
		}
	}

}
