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

	// RESOURCE_LOCALでは正常に動作しない
	//@Inject @UsedJpaLocal private BookDAO<EntityManager> bookdao
	@Inject @UsedJpaJta private BookDAO<EntityManager> bookdao;
	@Inject private Logger log;

	// BMTなのでトランザクションをUserTransactionで制御する
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
		// BMTではUserTransactionで管理されるため、emを引き継ぐ必要はなし
	}

	/*
	 * JSFの実装の都合上、selectedItemsには表示されているitem以外も設定されている
	 * このため、単純にaddAllでは、同じitemが複数登録される場合がある
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
