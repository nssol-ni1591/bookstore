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
		// ここでnullを指定してDAO実装クラス側でDIしたsessionFactoryを使用しても
		// Tx動作に違いはない。らしい
		//return sessionFactory
		return null;
	}

	// DIを実現するためのSetterメソッドs
	public void setCustomerdao(CustomerDAO<SessionFactory> customerdao) {
		this.customerdao = customerdao;
	}

	// コンテキスト.xmlにtransactionAttributesを定義しているので@Transactionalを省略できる
	@Override
	public boolean createCustomer(String inUid
			, String inPassword
			, String inName
			, String inEmail) throws Exception {
		//rollbackするための例外はRuntimeExceptionでないといけない
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
