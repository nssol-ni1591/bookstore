package bookstore.servlet3.weld;

import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;

import bookstore.annotation.UsedWeld;
import bookstore.service.BookService;
import bookstore.service.CustomerService;
import bookstore.servlet3.AbstractLoginServlet;

/*
 * ServletはScopeアノテーションを付加するとCDI管理下になる
 * @Injectの@Qualifierでservice層を切替える
 */
@WebServlet(urlPatterns="/LoginServlet3")
@RequestScoped
public class LoginServlet extends AbstractLoginServlet {

	private static final long serialVersionUID = 1L;

	// ServeletでServiceクラスを@Injectすると、
	// ServiceクラスはJPA用のDAOを@Injectしているので、
	// Service生成時にJPASelectorは生成済みである必要がある
	/*
	 * JPASelectorの初期化：
	 * service層のEntityManagerへの設定は、Service層クラスの@PostConstructで行われるため、
	 * service層クラスをDIするservletクラスの初期化時に行われる/
	 * よって、servletクラスの初期化時ではjpaModule/txTypeの値が未設定のためnull例外が発生する.
	 * 
	 * このため、JPASelectorの仕組みを利用できないので、
	 * JPASelectorを使用しないserviceクラス、
	 * つまり、使用するJPAモジュールが固定化されているService層なら利用できる
	 * 例えば、@UsedClassic
	 * 
	 * 20181015:
	 * JPASelectorからgetEntityManager()のcallタイミングを@PostConstractorから
	 * getEntityManager()の実装に変更することでnull例外の問題を回避
	 * 
	 * しかし、servlet内で更新したcommonは、customerServiceとbookServiceから呼び出した
	 * JPASelectorが参照するcommonのインスタンスが異なるようだ？
	 */
	@Inject @UsedWeld private CustomerService customerService;
	@Inject @UsedWeld private BookService bookService;
	@Inject private Logger log;


	@Override
	protected BookService getBookService() {
		return bookService;
	}
	@Override
	protected CustomerService getCustomerService() {
		return customerService;
	}
	@Override
	protected Logger getLogger() {
		return log;
	}
	@Override
	protected String getNextPage() {
		return "BookStore4.jsp";
	}
	@Override
	protected String getErrorPage() {
		return "Login4.jsp";
	}

}
