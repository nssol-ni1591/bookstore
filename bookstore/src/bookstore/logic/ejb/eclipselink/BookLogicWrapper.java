package bookstore.logic.ejb.eclipselink;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import bookstore.annotation.UsedJpaJta;
import bookstore.dao.BookDAO;
import bookstore.logic.BookLogic;
import bookstore.logic.AbstractBookLogic;

@Stateless(name="BookLogicEclipseLinkWrapper")
@LocalBean
@Local(BookLogic.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class BookLogicWrapper extends AbstractBookLogic<EntityManager> {

	@Inject @UsedJpaJta private BookDAO<EntityManager> bookdao;
	@Inject private Logger log;

	@Override
	protected BookDAO<EntityManager> getBookDAO() {
		return bookdao;
	}
	@Override
	protected Logger getLogger() {
		return log;
	}
	@Override
	protected EntityManager getManager() {
		return null;
		// BMT�ł�UserTransaction�ŊǗ�����邽�߁Aem�������p���K�v�͂Ȃ�
	}

	/*
	 * JSF�̎����̓s����AselectedItems�ɂ͕\������Ă���item�ȊO���ݒ肳��Ă���
	 * ���̂��߁A�P����addAll�ł́A����item�������o�^�����ꍇ������
	 * @see bookstore.logic.impl.BookLogicImpl#createCart(java.util.List, java.util.List, java.util.List)
	 */
	@Override
	public List<String> createCart(List<String> inProductList, List<String> inSelectedList, List<String> inCart) {
		if (inSelectedList != null && !inSelectedList.isEmpty()) {
			inSelectedList.stream()
				.filter(p -> !inCart.contains(p))
				.filter(inProductList::contains)
				.forEach(inCart::add);
		}
		return inCart;
	}

}
