package bookstore.service.weld;

import java.sql.SQLException;
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
import bookstore.vbean.VBook;
import bookstore.vbean.VCheckout;

@UsedWeld
@Dependent
public class BookServiceWrapper extends AbstractBookService<EntityManager> {

	@Inject @UsedJpa private BookDAO<EntityManager> bookdao;
	@Inject private Logger log;

	@Inject private JPASelector selector;
	private EntityManager em = null;


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
		// EntityTransactionのTx制御の都合で、1Tx内のemは同じインスタンスを使用しないといけない
		log.log(Level.INFO, "em={0}", em);
		return em;
	}

	private void startManager() {
		em = selector.getEntityManager();
		log.log(Level.INFO, "em={0}", em);
		if (em == null) {
			log.log(Level.INFO, "print stack trace", new Exception());
		}
	}
	private void stopManager() {
		this.em = null;
	}

	/*
	 * JSFの実装の都合上、selectedItemsには表示されているitem以外も設定されている
	 * このため、単純にaddAllでは、同じitemが複数登録される場合がある
	 * @see bookstore.service.impl.BookServiceImpl#createCart(java.util.List, java.util.List, java.util.List)
	 */
	@Override
	public List<String> createCart(List<String> productList, List<String> selectedList, List<String> cart) {
		startManager();
		if (selectedList != null && !selectedList.isEmpty()) {
			selectedList.stream()
				.filter(p -> !cart.contains(p))
				.filter(productList::contains)
				.forEach(cart::add);
		}
		stopManager();
		return cart;
	}

	@Override
	public List<String> getAllBookISBNs() throws SQLException {
		startManager();
		List<String> list = super.getAllBookISBNs();
		stopManager();
		return list;
	}

	@Override
	public List<String> retrieveBookISBNsByKeyword(String keyword) throws SQLException {
		startManager();
		List<String> list = super.retrieveBookISBNsByKeyword(keyword);
		stopManager();
		return list;
	}

	@Override
	public List<VBook> createVBookList(List<String> productList, List<String> selectedList) throws SQLException {
		startManager();
		List<VBook> list = super.createVBookList(productList, selectedList);
		stopManager();
		return list;
	}

	@Override
	public VCheckout createVCheckout(List<String> selectedList) throws SQLException {
		startManager();
		VCheckout vc = super.createVCheckout(selectedList);
		stopManager();
		return vc;
	}

}
