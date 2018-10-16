package bookstore.servlet3.classic;

import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;

import bookstore.annotation.UsedClassic;
import bookstore.service.BookService;
import bookstore.servlet3.AbstractSearchServlet;

/*
 * ServletはScopeアノテーションを付加するとCDI管理下になる
 * @Injectの@Qualifierでservice層を切替える
 */
@WebServlet(urlPatterns="/SearchServlet4")
@RequestScoped
public class SearchServlet extends AbstractSearchServlet {

	private static final long serialVersionUID = 1L;

	@Inject @UsedClassic private BookService bookService;
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
		return "BookStore4.jsp";
	}

}
