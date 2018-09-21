package bookstore.logic.weld;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import bookstore.annotation.UsedWeld;
import bookstore.annotation.UsedEclipselink;
import bookstore.dao.BookDAO;
import bookstore.logic.AbstractBookLogic;

@UsedWeld
@Dependent
public class BookLogicWrapper extends AbstractBookLogic<EntityManager> implements Serializable {

	/*
	 * bookstore.jsf.bean.BookStoreBeanのスコープが@SessionScopeのためSerializedが必要
	 */
	private static final long serialVersionUID = 1L;

	@Inject @UsedEclipselink private BookDAO<EntityManager> bookdao;
	@Inject private Logger log;

	//@PersistenceContext(unitName = "BookStore") private EntityManager em;
	@PersistenceUnit(name = "BookStore") private EntityManagerFactory emf;

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
		EntityManager em = emf.createEntityManager();
		return em;
	}

	/*
	 * JSFの実装の都合上、selectedItemsには表示されているitem以外も設定されている
	 * このため、単純にaddAllでは、同じitemが複数登録される場合がある
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
