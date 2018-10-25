package bookstore.dao.jpa.selector;

import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import bookstore.annotation.UsedJpaSelector;
import bookstore.dao.jpa.AbstractBookDAOImpl;

/*
 * Service�w�ł�CommonJSFBean���g�p����JPA�̎������W���[�������肷��
 * �d�g�݂Ƃ��ẮAService�w�Ŏ擾����EntityManager���e���\�b�h�̈���
 * �ƂȂ�̂ŁA���̒l���g�p����SQL�𔭍s����
 * �䂦�ɁA���̃N���X��EntityManager�̎擾�͕K�v�Ȃ��i�͂��ȁj�̂�
 * getEntityManager()��null��ԋp����
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
