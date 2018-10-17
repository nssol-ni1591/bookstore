package bookstore.servlet3.weld;

import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;

import bookstore.annotation.UsedWeld;
import bookstore.service.BookService;
import bookstore.servlet3.AbstractAddToCartServlet;

/*
 * ServletはScopeアノテーションを付加するとCDI管理下になる
 * @Injectの@Qualifierでservice層を切替える
 */
@WebServlet(urlPatterns="/AddToCartServlet3")
@RequestScoped
public class AddToCartServlet extends AbstractAddToCartServlet {

	private static final long serialVersionUID = 1L;

	@Inject @UsedWeld private BookService bookService;
	@Inject private Logger log;


	@Override
	protected BookService getBookService() {
		return bookService;
	}
	@Override
	protected Logger getLogger() {
		return log;
	}
	@Override
	protected String getNextPage() {
		return "BookStore3.jsp";
	}

}
