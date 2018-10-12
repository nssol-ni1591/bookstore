package bookstore.service.weld;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import bookstore.annotation.UsedWeld;
import bookstore.annotation.UsedJpa;
import bookstore.dao.BookDAO;
import bookstore.persistence.JPASelector;
import bookstore.service.AbstractBookService;

@UsedWeld
@Dependent
public class BookServiceWrapper extends AbstractBookService<EntityManager> {

	@Inject @UsedJpa private BookDAO<EntityManager> bookdao;
	@Inject private Logger log;

	@Inject private JPASelector selector;
	private EntityManager em = null;


	@PostConstruct
	public void init() {
		em = selector.getEntityManager();
		log.log(Level.INFO, "this={0}, em={1}", new Object[] { this, em });
	}

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
		// EntityTransaction��Tx����̓s���ŁA1Tx����em�͓����C���X�^���X���g�p���Ȃ��Ƃ����Ȃ�
		// �����ł́A���̃N���X�̃C���X�^���X�Ŏg�p����em�͓����C���X�^���X���Q�Ƃ��邱�ƂőΉ�����
		return em;
		// ������A�����ɕ����̃X���b�h�ł��̃C���X�^���X���g�p�����Ɠs��������
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
