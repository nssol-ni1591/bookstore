package bookstore.service.weld;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

	// JTA�ł�RESOURCE_LOCAL�ł�����ɓ��삷��iEntityTransaction���g�p���Ă���̓��R�j
	//@Inject @UsedJpaJta private BookDAO<EntityManager> bookdao
	//@Inject @UsedJpaLocal private BookDAO<EntityManager> bookdao
	@Inject @UsedJpa private BookDAO<EntityManager> bookdao;
	@Inject private Logger log;

	//@PersistenceUnit(name = "BookStore") private transient EntityManagerFactory emf
	//private EntityManager em = null
	@Inject private JPASelector selector;


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
		// em�͍X�VTx�̂�Service�w�Ő������邱�Ƃɂ���
		// ����āA�X�VTx�ȊO�ł�em�̒l��null�ƂȂ�̂�DAO�w�Ő��������
		//return em
		EntityManager em = selector.getEntityManager();
		log.log(Level.INFO, "this={0}, em={1}", new Object[] { this, em });
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
