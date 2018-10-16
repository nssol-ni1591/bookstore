package bookstore.servlet3.jpa;

import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;

import bookstore.annotation.UsedWeld;
import bookstore.service.BookService;
import bookstore.servlet3.AbstractCheckoutServlet;

/*
 * ServletはScopeアノテーションを付加するとCDI管理下になる
 * @Injectの@Qualifierでservice層を切替える
 */
@WebServlet(urlPatterns="/CheckoutServlet3")
@RequestScoped
public class CheckoutServlet extends AbstractCheckoutServlet {

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
		return "Check3.jsp";
	}
	@Override
	protected String getErrorPage() {
		return "BookStore3.jsp";
	}

}
