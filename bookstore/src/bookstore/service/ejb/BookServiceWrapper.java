package bookstore.service.ejb;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import bookstore.annotation.UsedJpa;
import bookstore.dao.BookDAO;
import bookstore.service.AbstractBookService;
import bookstore.service.BookService;

@Stateless(name="BookServiceEjbWrapper")
@LocalBean
@Local(BookService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class BookServiceWrapper extends AbstractBookService<EntityManager> {

	@Inject @UsedJpa private BookDAO<EntityManager> bookdao;

	@Inject private EntityManager em;
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
		// DAO�w�Ŏg�p����EntityManager���Œ艻���邽��
		return em;
	}

	/*
	 * JSF�̎����̓s����AselectedItems�ɂ͕\������Ă���item�ȊO���ݒ肳��Ă���
	 * ���̂��߁A�P����addAll�ł́A����item�������o�^�����ꍇ������
	 * @see bookstore.service.impl.BookServiceImpl#createCart(java.util.List, java.util.List, java.util.List)
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
