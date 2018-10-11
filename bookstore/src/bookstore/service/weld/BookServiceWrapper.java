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

	// JTAでもRESOURCE_LOCALでも正常に動作する（EntityTransactionを使用しているの当然）
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
		// emは更新TxのみService層で生成することにする
		// よって、更新Tx以外ではemの値はnullとなるのでDAO層で生成される
		//return em
		EntityManager em = selector.getEntityManager();
		log.log(Level.INFO, "this={0}, em={1}", new Object[] { this, em });
		return em;
	}

	/*
	 * JSFの実装の都合上、selectedItemsには表示されているitem以外も設定されている
	 * このため、単純にaddAllでは、同じitemが複数登録される場合がある
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
