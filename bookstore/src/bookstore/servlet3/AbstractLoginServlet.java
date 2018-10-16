package bookstore.servlet3;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bookstore.jsf.bean.CommonJSFBean;
import bookstore.service.BookService;
import bookstore.service.CustomerService;
import bookstore.util.Messages;
import bookstore.vbean.VBook;

/*
 * ServletはScopeアノテーションを付加するとCDI管理下になる
 * @Injectの@Qualifierでservice層を切替える
 */
public abstract class AbstractLoginServlet extends HttpServlet {

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
	protected abstract CustomerService getCustomerService();
	protected abstract BookService getBookService();
	protected abstract Logger getLogger();
	protected abstract String getNextPage();
	protected abstract String getErrorPage();

	@Inject private CommonJSFBean common;


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		CustomerService customerService = getCustomerService();
		BookService bookService = getBookService();
		Logger log = getLogger();
		String nextPage = getNextPage();
		String errorPage = getErrorPage();

		HttpSession httpSession = req.getSession(false);
		if (httpSession != null) {
			// sessionの初期化
			httpSession.invalidate();
		}
		httpSession = req.getSession();

		// CommonJSFBeanの初期化
		String jpaModule = req.getParameter("jpaModule");
		String txType = req.getParameter("txType");
		common.setJpaModule(jpaModule);
		common.setTxType(txType);
		log.log(Level.INFO, "jpaModule={0}, txType={1}", new Object[] { common.getJpaModule(), common.getTxType() });

		String account = req.getParameter("account");
		String passwd = req.getParameter("passwd");
		Messages errors = new Messages(req);
		RequestDispatcher dispatcher;
		try {
			// check password
			if (!customerService.isPasswordMatched(account, passwd)) {
				// Account mismatched
				errors.add("illegallogin", "error.login.pwmismatch");
				dispatcher = req.getRequestDispatcher(errorPage);
			}
			else {
				httpSession.setAttribute("Login", account);
				List<String> productListAll = bookService.getAllBookISBNs();
				List<VBook> vProductList = bookService.createVBookList(productListAll, null);
				//log.log(Level.INFO, "doPost: productListAll={0}", productListAll)
				//log.log(Level.INFO, "doPost: ProductListView={0}", vProductList)
				httpSession.setAttribute("ProductList", productListAll);
				httpSession.setAttribute("ProductListView", vProductList);
				dispatcher = req.getRequestDispatcher(nextPage);
			}
		}
		catch (SQLException e) {
			log.log(Level.SEVERE, "", e);
			errors.add("illegallogin", "error.system.exception");
			dispatcher = req.getRequestDispatcher(errorPage);
		}
		try {
			dispatcher.forward(req, res);
		}
		catch (ServletException | IOException e) {
			log.log(Level.SEVERE, "", e);
		}
	}

}
