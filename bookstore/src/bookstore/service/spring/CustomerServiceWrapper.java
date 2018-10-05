package bookstore.service.spring;

import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import bookstore.annotation.Log;
import bookstore.annotation.UsedSpring;
import bookstore.dao.CustomerDAO;
import bookstore.service.AbstractCustomerService;

@UsedSpring
@Component("ServiceCustomerImplBId")
public class CustomerServiceWrapper extends AbstractCustomerService<SessionFactory> {

	@Log private static Logger log;

	private CustomerDAO<SessionFactory> customerdao;
	//private SessionFactory sessionFactory

	@Override
	protected CustomerDAO<SessionFactory> getCustomerDAO() {
		return customerdao;
	}
	@Override
	protected Logger getLogger() {
		return log;
	}
	@Override
	protected SessionFactory getManager() {
		// ������null���w�肵��DAO�����N���X����DI����sessionFactory���g�p���Ă�
		// Tx����ɈႢ�͂Ȃ��B�炵��
		//return sessionFactory
		return null;
	}

	// DI���������邽�߂�Setter���\�b�hs
	public void setCustomerdao(CustomerDAO<SessionFactory> customerdao) {
		this.customerdao = customerdao;
	}

	// �R���e�L�X�g.xml��transactionAttributes���`���Ă���̂�@Transactional���ȗ��ł���
	@Override
	public boolean createCustomer(String inUid
			, String inPassword
			, String inName
			, String inEmail) throws Exception {
		//rollback���邽�߂̗�O��RuntimeException�łȂ��Ƃ����Ȃ�
		try {
			return super.createCustomer(inUid, inPassword, inName, inEmail);
		}
		catch (RuntimeException e) {
			throw e;
		}
		catch (Exception e) {
			throw new SpringRuntimeException(e);
		}
	}

}
