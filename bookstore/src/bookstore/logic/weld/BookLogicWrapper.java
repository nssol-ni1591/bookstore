package bookstore.logic.weld;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import bookstore.annotation.UsedWeld;
import bookstore.annotation.UsedEclipselink;
import bookstore.dao.BookDAO;
import bookstore.logic.AbstractBookLogic;

@UsedWeld
@Dependent
public class BookLogicWrapper extends AbstractBookLogic implements Serializable {

	/*
	 * bookstore.jsf.bean.BookStoreBean�̃X�R�[�v��@SessionScope�̂���Serialized���K�v
	 */
	private static final long serialVersionUID = 1L;

	@Inject @UsedEclipselink private BookDAO bookdao;
	@Inject private Logger log;

	@Override
	protected BookDAO getBookDAO() {
		return bookdao;
	}
	@Override
	protected Logger getLogger() {
		return log;
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
