package bookstore.dao.jpa.selector;

import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import bookstore.annotation.UsedJpaSelector;
import bookstore.dao.jpa.AbstractBookDAOImpl;

/*
 * Service層でのCommonJSFBeanを使用してJPAの実装モジュールを決定する
 * 仕組みとしては、Service層で取得したEntityManagerが各メソッドの引数
 * となるので、この値を使用してSQLを発行する
 * ゆえに、このクラスでEntityManagerの取得は必要ない（はずな）ので
 * getEntityManager()はnullを返却する
 */
@UsedJpaSelector
@Dependent
public class BookDAOImpl<T extends EntityManager> extends AbstractBookDAOImpl<T> {

	@Inject private Logger log; 

	@Override
	protected EntityManager getEntityManager() {
		return null;
	}

	@Override
	protected Logger getLogger() {
		return log;
	}

}
