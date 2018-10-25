package bookstore.service.weld;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import bookstore.annotation.UsedWeld;
import bookstore.annotation.WithEntityTx;
import bookstore.annotation.UsedJpaSelector;
import bookstore.dao.BookDAO;
import bookstore.persistence.JPASelector;
import bookstore.service.AbstractBookService;
import bookstore.util.EntityTx;
import bookstore.vbean.VBook;
import bookstore.vbean.VCheckout;

@UsedWeld
@Dependent
public class BookServiceWrapper extends AbstractBookService<EntityManager> implements EntityTx {

	@Inject @UsedJpaSelector private BookDAO<EntityManager> bookdao;
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
	public EntityManager getManager() {
		// EntityTransactionのTx制御の都合で、1Tx内のemは同じインスタンスを使用しないといけない
		log.log(Level.INFO, "em={0}", em);
		return em;
	}

	@Override
	public void startEntityTx() {
		em = selector.getEntityManager();
	}
	@Override
	public void stopEntityTx() {
		this.em = null;
	}

	/*
	 * JSFの実装の都合上、selectedItemsには表示されているitem以外も設定されている
	 * このため、単純にaddAllでは、同じitemが複数登録される場合がある
	 * @see bookstore.service.impl.BookServiceImpl#createCart(java.util.List, java.util.List, java.util.List)
	 */
	@Override
	@WithEntityTx
	public List<String> createCart(List<String> productList, List<String> selectedList, List<String> cart) {
		if (selectedList != null && !selectedList.isEmpty()) {
			selectedList.stream()
				.filter(p -> !cart.contains(p))
				.filter(productList::contains)
				.forEach(cart::add);
		}
		return cart;
	}

	@Override
	@WithEntityTx
	public List<String> getAllBookISBNs() throws SQLException {
		return super.getAllBookISBNs();
	}

	@Override
	@WithEntityTx
	public List<String> retrieveBookISBNsByKeyword(String keyword) throws SQLException {
		return super.retrieveBookISBNsByKeyword(keyword);
	}

	@Override
	@WithEntityTx
	public List<VBook> createVBookList(List<String> productList, List<String> selectedList) throws SQLException {
		return super.createVBookList(productList, selectedList);
	}

	@Override
	@WithEntityTx
	public VCheckout createVCheckout(List<String> selectedList) throws SQLException {
		return super.createVCheckout(selectedList);
	}

}
