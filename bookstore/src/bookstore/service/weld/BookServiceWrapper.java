package bookstore.service.weld;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import bookstore.annotation.UsedWeld;
import bookstore.annotation.UsedJpaJta;
import bookstore.dao.BookDAO;
import bookstore.service.AbstractBookService;

@UsedWeld
@Dependent
public class BookServiceWrapper extends AbstractBookService<EntityManager> implements Serializable {

	/*
	 * bookstore.jsf.bean.BookStoreBeanのスコープが@SessionScopeのためSerializedが必要
	 */
	private static final long serialVersionUID = 1L;

	@Inject @UsedJpaJta private BookDAO<EntityManager> bookdao;
	@Inject private Logger log;

	@PersistenceUnit(name = "BookStore") private transient EntityManagerFactory emf;

	private transient EntityManager em = null;

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
