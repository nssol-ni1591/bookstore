package bookstore.logic.weld;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import bookstore.annotation.UsedWeld;
import bookstore.annotation.UsedEclipselink;
import bookstore.dao.BookDAO;
import bookstore.logic.impl.BookLogicImpl;

@UsedWeld
public class BookLogicWrapper extends BookLogicImpl implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject @UsedEclipselink private BookDAO bookdao;

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
		/*
		inCart.removeAll(inProductList);
		if (inSelectedList != null && !inSelectedList.isEmpty()) {
			inCart.addAll(inSelectedList)
		}
		*/
		//inCart.removeAll(inProductList);
		if (inSelectedList != null && !inSelectedList.isEmpty()) {
			inSelectedList.stream()
				.filter(p -> !inCart.contains(p))
				.filter(inProductList::contains)
				.forEach(inCart::add);
		}
		return inCart;
	}

}
