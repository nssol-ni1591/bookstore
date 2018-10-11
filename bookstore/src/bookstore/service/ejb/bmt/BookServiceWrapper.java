package bookstore.service.ejb.bmt;

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
import bookstore.service.AbstractBookService;
import bookstore.service.BookService;

@Stateless(name="BookServiceBmtWrapper")
@LocalBean
@Local(BookService.class)
@TransactionManagement(TransactionManagementType.BEAN)
public class BookServiceWrapper extends AbstractBookService<EntityManager> {

	// RESOURCE_LOCAL�ł͐���ɓ��삵�Ȃ�
	//@Inject @UsedJpaLocal private BookDAO<EntityManager> bookdao
	@Inject @UsedJpaJta private BookDAO<EntityManager> bookdao;
	@Inject private Logger log;

	// BMT�Ȃ̂Ńg�����U�N�V������UserTransaction�Ő��䂷��
	//@Resource private UserTransaction tx

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
	 * @see bookstore.service.impl.BookServiceService#createCart(java.util.List, java.util.List, java.util.List)
	 */
	@Override
	public List<String> createCart(List<String> productList, List<String> selectedList, List<String> cart) {
		if (selectedList != null && !selectedList.isEmpty()) {
			selectedList.stream()
				.filter(p -> !cart.contains(p))
				.filter(productList::contains)
				.forEach(cart::add);
		}
		return cart;
	}

}
