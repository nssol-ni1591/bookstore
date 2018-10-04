package bookstore.service.ejb.bmt;

import bookstore.annotation.UsedJpaLocal;
import bookstore.dao.BookDAO;
import bookstore.service.AbstractBookService;
import bookstore.service.BookService;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless(name="BookServiceBmtWrapper")
@LocalBean
@Local(BookService.class)
@TransactionManagement(TransactionManagementType.BEAN)
public class BookServiceWrapper extends AbstractBookService<EntityManager> {

	@Inject @UsedJpaLocal private BookDAO<EntityManager> bookdao;
	@Inject private Logger log;

	// UserTransactionはBMTに対するものでCMTには利用できない
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
