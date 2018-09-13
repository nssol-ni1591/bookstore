package bookstore.logic.ejb;

import bookstore.annotation.UsedEjb;
import bookstore.annotation.UsedOpenJpa;
import bookstore.dao.BookDAO;
import bookstore.logic.impl.BookLogicImpl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

@UsedEjb
@Stateless
public class BookLogicWrapper extends BookLogicImpl {

	/*
	 * bookstore.jsf.bean.BookStoreBeanのスコープが@SessionScopeのためSerializedが必要
	 */
	//private static final long serialVersionUID = 1L

	@Inject @UsedOpenJpa private BookDAO bookdao;

	@PostConstruct
	public void init() {
		super.setBookdao(bookdao);
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
