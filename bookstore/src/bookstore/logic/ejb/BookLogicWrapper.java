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
	 * bookstore.jsf.bean.BookStoreBean�̃X�R�[�v��@SessionScope�̂���Serialized���K�v
	 */
	//private static final long serialVersionUID = 1L

	@Inject @UsedOpenJpa private BookDAO bookdao;

	@PostConstruct
	public void init() {
		super.setBookdao(bookdao);
	}

	/*
	 * JSF�̎����̓s����AselectedItems�ɂ͕\������Ă���item�ȊO���ݒ肳��Ă���
	 * ���̂��߁A�P����addAll�ł́A����item�������o�^�����ꍇ������
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
